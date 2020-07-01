package com.server.restaurantrent.services;

import com.server.restaurantrent.models.AuthToken;
import com.server.restaurantrent.repo.AuthTokenRepository;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@Service
public class AuthMessageService {
    private String email;
    private long idUser;
    private AuthTokenRepository authTokenRepository;

    public AuthMessageService() {
    }

    public AuthMessageService(String email, long idUser, AuthTokenRepository authTokenRepository) {
        this.email = email;
        this.idUser = idUser;
        this.authTokenRepository = authTokenRepository;
    }

    public boolean sendAuthMessage() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("restaurantrent0@gmail.com", "ServerPass");
                    }
                });

        try {
            // отправляем владельцу на электронную почту письмо
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("restaurantrent0@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Подтверждение электронной почты");
            // генерируем случайный токен
            String token = UUID.randomUUID().toString();
            message.setText("Добро пожаловать!," +
                    "\n\n Чтобы подтвердить адрес электронной почты, перейдите по ссылке https://restaurant-rent-server.herokuapp.com/account/confirm/" + token);
            Transport.send(message);

            // сохраняем полученный токен в базе данных
            authTokenRepository.save(new AuthToken(token, idUser));

            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}
