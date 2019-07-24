package com.example.spring.jobweb.mvc.services;


import com.example.spring.jobweb.utils.ApplicationState;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class EmailService {
    private final static  String companymail = "obssisbasvuru@gmail.com";
    private final static String password = "1462obss";
    private static  String text(int status){
        if(status== ApplicationState.ACCEPTING.getValue()){
            return "olumlu sonuçlanmıştır, tebrikler. Görüşme zamanını belirlemek için aranacaksınız.\n";
        }
        else if(status==ApplicationState.WAITING.getValue()){
            return "değerlendirme aşamasındadır. Başvurunuz için teşekkür ederiz\n";
        }
        else {
            return "malesef olumlu sonuçlanmamıştır. Daha sonra yeniden görüşmek dileğiyle\n";
        }
    }
    public static void send(String userName,String userSurname,String email,String jobName,int status){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        System.out.println("girdim");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(companymail, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(companymail));
            message.setRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(email)
            );
            message.setSubject(userName+" "+userSurname+"-OBSS İş Başvurusu Değerlendirmesi");
            message.setText(userName+" "+userSurname+" "+jobName+" işi için başvurunuz "+" "+text(status));

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
