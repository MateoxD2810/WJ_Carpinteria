package com.wjcarpi.servicio;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRecoveryCode(String email, String recoveryCode) {
        String subject = "Recuperación de Contraseña";
        String message = "Hola,\n\nTu código de recuperación es: " + recoveryCode + "\n\nPor favor, utilízalo para cambiar tu contraseña.";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, false);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo electrónico", e);
        }
    }

    // Método para el envío masivo de correos
    public void sendBulkEmail(String[] recipients, String subject, String messageBody) {
        try {
            // Crear el mensaje de correo
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(subject);
            helper.setText(messageBody, false);
            
            // Enviar el correo a cada destinatario
            for (String recipient : recipients) {
                helper.setTo(recipient);
                mailSender.send(mimeMessage); // Enviar el correo
            }
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo masivo", e);
        }
    }

}
