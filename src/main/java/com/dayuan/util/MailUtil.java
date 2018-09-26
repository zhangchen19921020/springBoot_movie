package com.dayuan.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 使用qq邮件服务器发送邮件
 */
public class MailUtil {

    private static String mail_server = "1299654941@qq.com";
    //授权码
    private static String password = "dmkeuvdhgdudgaei";

    /**
     * 发送邮件工具
     *
     * @param to
     * @param subject
     * @param msg
     */
    public static void sendTextMail(String to, String subject, String msg) {
        // 发件人电子邮箱
        String from = mail_server;
        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        // 配置邮件服务器
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail_server, password); //发件人邮件用户名、密码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject(subject);

            // 设置消息体
            message.setText(msg);

            // 发送消息
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}

class TestMail {
    public static void main(String[] args) {
        MailUtil.sendTextMail("1299654941@qq.com", "test", "【大猿国际影城】您的验证码：哈哈哈");
    }
}