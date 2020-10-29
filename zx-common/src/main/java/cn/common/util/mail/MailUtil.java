package cn.common.util.mail;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件发送工具实现类
 *
 */
@Component
@Slf4j
public class MailUtil {
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.host}")
    private String host;

    private int port;
    private MailAddress from;

    public MailUtil() {
        port = 25;
    }

    public void setFrom(MailAddress from) {
        this.from = from;
    }

    public boolean send(MailMessageObject message) {
        boolean rslt = false;

        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);// 设置SMTP的主机
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", true);// 是否需要经过验证

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            MimeMessage msg = new MimeMessage(session);
            InternetAddress f = new InternetAddress();
            f.setAddress(from.getAddress());
            f.setPersonal(from.getName());
            msg.setFrom(f);

            if (message.getTo() != null && message.getTo().size() > 0) {
                InternetAddress[] toAddr = new InternetAddress[message.getTo().size()];
                for (int i = 0; i < message.getTo().size(); i++) {
                    toAddr[i] = new InternetAddress(message.getTo().get(i).getAddress(),
                            message.getTo().get(i).getName());
                }
                msg.setRecipients(Message.RecipientType.TO, toAddr);
            }

            if (message.getCc() != null && message.getCc().size() > 0) {
                InternetAddress[] cc = new InternetAddress[message.getCc().size()];
                for (int i = 0; i < message.getCc().size(); i++) {
                    cc[i] = new InternetAddress(message.getCc().get(i).getAddress(),
                            message.getCc().get(i).getName());
                }
                msg.setRecipients(Message.RecipientType.CC, cc);
            }
            Multipart multipart = new MimeMultipart();
            if(StringUtils.isNotEmpty(message.getContext())){
                BodyPart contentPart = new MimeBodyPart();
                contentPart.setContent(message.getContext(), "text/html;charset=UTF-8");
                multipart.addBodyPart(contentPart);
            }
            if (message.getFileList() != null) {
                for (File file : message.getFileList()) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(file);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    attachmentBodyPart.setFileName(file.getName());
                    multipart.addBodyPart(attachmentBodyPart);
                }

            }

            msg.setSubject(message.getSubject());
            msg.setContent(multipart);
            msg.setSentDate(new Date());
            msg.saveChanges();
            Transport.send(msg);
            rslt = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            rslt = false;
        }

        return rslt;
    }
}
