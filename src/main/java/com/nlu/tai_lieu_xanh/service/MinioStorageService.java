package com.nlu.tai_lieu_xanh.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

@Service
public class MinioStorageService {
  private final MinioClient minioClient;
  @Value("${minio.bucket}")
  private String bucketName;

  public MinioStorageService(MinioClient minioClient) {
    this.minioClient = minioClient;
  }

  public void uploadFile(String objectName, MultipartFile file)
      throws IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
      InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException {
    try (var inputStream = file.getInputStream()) {
      var object = PutObjectArgs.builder()
          .bucket(bucketName)
          .object(objectName)
          .stream(inputStream, file.getSize(), -1)
          .contentType(file.getContentType())
          .build();
      minioClient.putObject(object);
    }
  }

  public InputStream downLoadFile(String objectName)
      throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
      InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
    var object = GetObjectArgs
        .builder().bucket(bucketName)
        .object(objectName)
        .build();
    return minioClient.getObject(object);

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

  public String getFileUrl(String objectName)
      throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
      InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ServerException, IOException {
    int expiredSeconds = 60 * 60;
    var object = GetPresignedObjectUrlArgs.builder()
        .method(Method.GET)
        .bucket(bucketName)
        .object(objectName)
        .expiry(expiredSeconds)
        .build();
    return minioClient.getPresignedObjectUrl(object);
  }
}
