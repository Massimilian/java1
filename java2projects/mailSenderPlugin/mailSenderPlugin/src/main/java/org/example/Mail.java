package org.example;

import org.apache.maven.plugins.annotations.Parameter;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Mail {

    public void send(String mailTo) {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.yandex.ru");
        properties.put("mail.smtps.auth", "true");
        properties.put("mail.smtp.sendpartial", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("masvas@mail.ru", "qetupoi1357098");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("masvas@mail.ru"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
            message.setSubject("Changes catch");

            MimeBodyPart first = new MimeBodyPart();
            first.setText("Text part of the message.");
            MimeBodyPart second = new MimeBodyPart();
            FileDataSource fds = new FileDataSource("D:/progwards/java2/src/ru/progwards/java2/lessons/builders/target/SimpleCalculator-1.0-SNAPSHOT.jar");
            second.setDataHandler(new DataHandler(fds));
            second.setFileName(fds.getName());
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(first);
            mp.addBodyPart(second);
            message.setContent(mp);

            Transport transport = session.getTransport();
            transport.connect("smtp.mail.ru", 465, "masvas@mail.ru", "qetupoi1357098");
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

