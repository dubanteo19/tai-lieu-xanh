package com.nlu.tai_lieu_xanh.infrastructure.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.tai_lieu_xanh.exception.FileStorageException;
import com.nlu.tai_lieu_xanh.exception.FileUploadException;

import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MinioStorageService {
  private final MinioClient minioClient;
  @Value("${minio.bucket}")
  private String bucketName;

  public MinioStorageService(MinioClient minioClient) {
    this.minioClient = minioClient;
  }

  public String generateObjectName(Integer userId, String folder, String originalFileName) {
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    String uuid = UUID.randomUUID().toString().substring(0, 8);
    String sanitizedFileName = originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    return String.format("%s/%d/%s-%s-%s", folder, userId, timestamp, uuid, sanitizedFileName);
  }

  public void uploadFile(String bucket, String objectName, ByteArrayOutputStream baos, String contentType) {
    try {
      var object = PutObjectArgs.builder()
          .bucket(bucket)
          .object(objectName)
          .stream(new ByteArrayInputStream(baos.toByteArray()), baos.size(), -1)
          .contentType(contentType)
          .build();
      minioClient.putObject(object);
    } catch (Exception e) {
      log.error("Failed to upload document {}", objectName);
      throw new FileUploadException("Can not upload document", e);
    }
  }

  public String uploadFile(Integer userId, MultipartFile file) {
    String objectName = generateObjectName(userId, "doc", file.getOriginalFilename());
    try (var inputStream = file.getInputStream()) {
      var object = PutObjectArgs.builder()
          .bucket(bucketName)
          .object(objectName)
          .stream(inputStream, file.getSize(), -1)
          .contentType(file.getContentType())
          .build();
      minioClient.putObject(object);
      return objectName;
    } catch (Exception e) {
      log.error("Failed to upload document {}", objectName);
      throw new FileUploadException("Can not upload document", e);
    }
  }

  public InputStream downLoadFile(String objectName) {
    try {
      var object = GetObjectArgs
          .builder().bucket(bucketName)
          .object(objectName)
          .build();
      return minioClient.getObject(object);
    } catch (Exception e) {
      log.error("Failed to download document {}", objectName);
      throw new FileUploadException("Can not download document", e);
    }
  }

  public void deleteFile(String objectName)
      throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
      InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
    var object = RemoveObjectArgs.builder()
        .bucket(bucketName)
        .object(objectName)
        .build();
    minioClient.removeObject(object);
  }

  public String getFileUrl(String objectName) {
    int expiredSeconds = 60 * 60;
    Map<String, String> extraHeaders = new HashMap<>();
    extraHeaders.put("response-content-disposition", "attachment; filename=\"" + objectName + "\"");

    var object = GetPresignedObjectUrlArgs.builder()
        .method(Method.GET)
        .bucket(bucketName)
        .object(objectName)
        .expiry(expiredSeconds)
        .extraQueryParams(extraHeaders)
        .build();
    try {
      return minioClient.getPresignedObjectUrl(object);
    } catch (Exception e) {
      log.error("Failed to generate presigned URL for object '{}'", objectName, e);
      throw new FileStorageException("Could not genrerate presigned URL for object " + objectName, e);
    }
  }
}
