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

    public String uploadFile(InputStream inputStream, Integer userId, String fileName) {
        String remotePath = "/user-" + userId + "/" + fileName;
        //Make new dir if user does not have a dir
        try (var session = ftpFileSessionFactory.getSession()) {
            if (!session.exists("/user-" + userId)) {
                session.mkdir("/user-" + userId);
            }
            //write file to ftp
            session.write(inputStream, remotePath);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return remotePath;
    }

    public void downloadFile(String remotePath, OutputStream outputStream) {
        try (var session = ftpFileSessionFactory.getSession()) {
            if (!session.exists(remotePath)) {
                throw new RuntimeException("file not found");
            }
            session.read(remotePath, outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()
            );
        }
    }
}
