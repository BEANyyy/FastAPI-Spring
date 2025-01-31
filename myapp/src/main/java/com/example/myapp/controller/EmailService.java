package com.example.myapp.controller;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        // MimeMessage 객체 생성
        MimeMessage message = mailSender.createMimeMessage();

        // MimeMessageHelper로 이메일 구성
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // 두 번째 매개변수에 true를 전달하여 HTML 형식 활성화

        // 이메일 발송
        mailSender.send(message);
    }
}