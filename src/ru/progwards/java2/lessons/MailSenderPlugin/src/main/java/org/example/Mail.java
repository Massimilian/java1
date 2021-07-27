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
    private final String smtp;
    private final String from;
    private final String to;
    private final String password;

    public Mail(String smtp, String from, String to, String password) {
        this.smtp = smtp;
        this.from = from;
        this.to = to;
        this.password = password;
    }

    public void execute() {
        // 1. Установим настройки для грамотной отработки smtp-сервера gmail
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", smtp);
        properties.put("mail.smtps.auth", "true");
        properties.put("mail.smtp.sendpartial", "true");

        // 2. Создадим сессию - объект, который будет заниматься отправкой сообщения. Сообщение создадим позже
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // 3. Создаём message - сообщение.
        try {
            Message message = new MimeMessage(session); // создаём сообщение
            message.setFrom(new InternetAddress(from)); // от кого
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // кому
            message.setSubject("Jar was done!"); // тема письма

            // 4. Присоединение файла
            MimeBodyPart first = new MimeBodyPart(); // формируем части письма. Часть первая.
            first.setText("I've seen that the new .jar was formed. I'm sending it for you. File is here."); // Текстовая.
            MimeBodyPart second = new MimeBodyPart(); // Часть вторая.
            FileDataSource fds = new FileDataSource("../builders/target/SimpleCalculator-1.0.jar"); // Файловая.
//            FileDataSource fds = new FileDataSource("../builders/src/main/java/File.txt"); // Тестовая отработка.
            second.setDataHandler(new DataHandler(fds)); // Присоединяем файл.
            second.setFileName(fds.getName());
            Multipart mp = new MimeMultipart(); // Создаём единый блок из частей
            mp.addBodyPart(first);
            mp.addBodyPart(second);
            message.setContent(mp); // Присоединяем получившийся блок к письму

            // 5. Отправляем сообщение.
            Transport transport = session.getTransport(); // объект для посылки письма
            transport.connect(smtp, 465, from, password); // соединение с почтой
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO)); // отправка
            transport.close(); // закрываем транспорт
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
