package com.nlu.tai_lieu_xanh.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FtpService {
    SessionFactory<FTPFile> ftpFileSessionFactory;


    public void downloadFile(String url, OutputStream outputStream) {
        try (var session = ftpFileSessionFactory.getSession()) {
            if (!session.exists(url)) {
                throw new RuntimeException("file not found");
            }
            session.dirty();
            session.read(url, outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()
            );
        }
    }

    public String uploadTempFile(InputStream inputStream, String fileName, Integer userId) {
        System.out.println("--------------------uploadTempFile" + fileName);
        var userFolder = "user-" + userId;
        String remotePath = "/Docs/" + userFolder + "/" + fileName;
        try (var session = ftpFileSessionFactory.getSession()) {
            if (!session.exists("/Docs/" + userFolder)) {
                session.mkdir("/Docs/" + userFolder);
            }
            session.write(inputStream, remotePath);
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return remotePath;
    }

}
