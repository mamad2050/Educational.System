package sample;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class SendMail {


    public  static  StringBuilder sb = new StringBuilder();
    public void sendEmailTo(String receiver) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "matin.zn24@gmail.com";
        String password = "matinzn24";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, receiver);
        assert message != null;
        Transport.send(message);
        System.out.println("Message sent successfully!");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String receiver) {

        String code = generateCode();

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject("change password");
            message.setText("Hey Your new password is : " + code);

            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateCode() {
        String alphabet = "1234567890";

        Random random = new Random();
        int length = 6;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}