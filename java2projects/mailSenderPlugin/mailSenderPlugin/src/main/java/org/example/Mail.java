package org.example;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Mail {
    public void send() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.gmail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("mail.smtp.sendpartial", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("masvas84@gmail.com", "55534442ttterrrw");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("masvas84@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("vasalekmas@gmail.com")); // кому
            message.setSubject("Changes catch");

            // 4. Присоединение файла
            MimeBodyPart first = new MimeBodyPart();
            first.setText("Text part of the message.");
            MimeBodyPart second = new MimeBodyPart();
//            FileDataSource fds = new FileDataSource("D:/progwards/java2/src/ru/progwards/java2/lessons/builders/target/builders-1.0-SNAPSHOT.jar");
            FileDataSource fds = new FileDataSource("D:/progwards/java2/src/ru/progwards/java2/lessons/builders/pom.xml");
            second.setDataHandler(new DataHandler(fds));
            second.setFileName(fds.getName());
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(first);
            mp.addBodyPart(second);
            message.setContent(mp);

            // 5. Отправляем сообщение.
            Transport transport = session.getTransport();
            transport.connect("smtp.gmail.com", 465, "masvas84@gmail.com", "55534442ttterrrw");
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

