package com.nlu.tai_lieu_xanh.config;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

@Configuration
public class FtpConfig {
  @Value("${ftp.host}")
  private String ftpHost;

  @Value("${ftp.port}")
  private int ftpPort;

  @Value("${ftp.user}")
  private String ftpUser;

  @Value("${ftp.password}")
  private String ftpPassword;

  @Bean
  SessionFactory<FTPFile> ftpFileSessionFactory() {
    DefaultFtpSessionFactory sessionFactory = new DefaultFtpSessionFactory();
    sessionFactory.setHost(ftpHost);
    sessionFactory.setPort(ftpPort);
    sessionFactory.setUsername(ftpUser);
    sessionFactory.setPassword(ftpPassword);
    return new CachingSessionFactory<>(sessionFactory);
  }
}
