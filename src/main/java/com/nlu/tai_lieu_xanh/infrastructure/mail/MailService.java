package com.nlu.tai_lieu_xanh.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendPasswordResetEmail(String to, String newPassword) throws MessagingException, UnsupportedEncodingException {
        Context context = new Context();
        context.setVariable("newPassword", newPassword);
        String htmlContent = templateEngine.process("password-reset-email", context);
        sendEmail(to, "Yêu cầu mật khẩu mới", htmlContent);
    }

    public void sendVerificationEmail(String to, String token) throws MessagingException, UnsupportedEncodingException {
        Context context = new Context();
        context.setVariable("token", token);
        String htmlContent = templateEngine.process("verification-mail", context);
        executorService.submit(() -> {
            try {
                sendEmail(to, "Kích hoạt tài khoản mới", htmlContent);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void sendEmail(String to, String subject, String text) throws MessagingException, UnsupportedEncodingException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            InternetAddress fromAddress = new InternetAddress("dubanteo2003@gmail.com", "Tài liệu xanh");
            mimeMessage.setFrom(fromAddress);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


}
