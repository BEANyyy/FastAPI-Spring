package com.example.myapp.controller;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendHtml")
    public String sendHtmlEmail(@RequestParam String email, @RequestParam String subject, @RequestParam String message) {
        String htmlContent = "<h1>Welcome!</h1><p>" + message + "</p>";
        System.out.println("message :");
        System.out.println(" :");
        System.out.println(message);
        try {
            emailService.sendHtmlEmail(email, subject, htmlContent);
            return "HTML Email sent successfully to: " + email;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}
