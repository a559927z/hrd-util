package net.chinahrd.utils.eMail;

import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.sun.mail.util.MailSSLSocketFactory;

import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.crypto.CryptUtils;
import net.chinahrd.utils.db.DatabaseUtil;

/**
 * 封装SpringMail工具类
 *
 * @author jxzhang on 2016年12月20日
 * @author jxzhang on 2017年04月16日
 * @Verdion 1.0 版本
 * @Verdion 2.0 版本
 */
@Component
public class MailUtil {

    private static final Logger log = LoggerFactory.getLogger(MailUtil.class);

//	@Value("${mail.url}")
//	private String url;
//	@Value("${mail.username}")
//	private String user;
//	@Value("${mail.password}")
//	private String pwd;
//	@Value("${mail.driver}")
//	private String driverClassName;

    private String url = "jdbc:mysql://40.125.213.43:3369/product_zrw?allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&zeroDateTimeBehavior=convertToNull";
    private String user = "product";
    private String pwd = "zrw1409";
    private String driverClassName = "com.mysql.jdbc.Driver";

    private static String host;
    private static String prot;
    private static String from;
    private static String account;
    private static String password;

    // 由调用处提供
    // private static List<String> to;
    // private static String title;
    // private static String content;

    /**
     * 组件JavaMail
     *
     * @return
     */
    private JavaMailSenderImpl wrapJavaMailSenderImpl() {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

        DatabaseUtil db = new DatabaseUtil(url, user, pwd, driverClassName);
        try {
            String sql = "SELECT min(t1.account) account, min(t1.pwd) password, min(t1. PORT) port, min(t1. HOST) host, min(t1.adminMail) adminMail FROM ( SELECT IF ( t.config_key = 'XTSZ-eMailAccount', t.config_val, NULL ) AS account, IF ( t.config_key = 'XTSZ-eMailPassword', t.config_val, NULL ) AS pwd, IF ( t.config_key = 'XTSZ-eMailPort', t.config_val, NULL ) AS PORT, IF ( t.config_key = 'XTSZ-eMailHost', t.config_val, NULL ) AS HOST, IF ( t.config_key = 'XTSZ-adminMail', t.config_val, NULL ) AS adminMail FROM `config` t WHERE t.config_key LIKE 'XTSZ-%' ) t1";

            db.query(sql);
            ResultSet rs = db.query(sql);
            while (rs.next()) {
                host = rs.getString("host");
                prot = rs.getString("port");
                from = rs.getString("adminMail");
                account = rs.getString("account");
                password = CryptUtils.decryptString(rs.getString("password"));
            }
            // 设定mail server
            senderImpl.setHost(host);
            senderImpl.setPort(Integer.parseInt(prot));
            senderImpl.setUsername(account);
            senderImpl.setPassword(password);
        } catch (Exception e) {
            log.info(e.toString());
        } finally {
            db.close();
        }
        return senderImpl;
    }

    /**
     * 发送邮件
     *
     * @param to      发送给谁
     * @param title   标题
     * @param content 内容
     */
    public void send(String to, String title, String content) {
        send(Arrays.asList(to), title, content);
    }

    /**
     * 发送邮件
     *
     * @param toList  发送给谁
     * @param title   标题
     * @param content 内容
     */
    public void send(List<String> toList, String title, String content) {
        JavaMailSenderImpl senderImpl = wrapJavaMailSenderImpl();
        // 设定mail server
        senderImpl.setHost(host);
        senderImpl.setPort(Integer.parseInt(prot));
        senderImpl.setUsername(account);
        senderImpl.setPassword(password);

        // 建立邮件消息
        // SimpleMailMessage mailMessage = new SimpleMailMessage();
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "utf-8");
        String to = "";
        // 发送邮件
        // 设置收件人，寄件人 用数组发送多个邮件
        String[] sendToArr = CollectionKit.listToStrArr(toList);
        for (String arr : sendToArr) {
            to = arr;
            log.info("由：" + account + "发送给：" + to);
            send(senderImpl, mailMessage, messageHelper, arr, title, content);
        }
    }

    /**
     * 核心-发送邮件
     *
     * @param senderImpl
     * @param mailMessage
     * @param messageHelper
     * @param to            发送给谁
     * @throws MessagingException
     * @throws GeneralSecurityException
     */
    private void send(JavaMailSenderImpl senderImpl, MimeMessage mailMessage, MimeMessageHelper messageHelper,
                      String to, String title, String content) {

        try {
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(title);
            messageHelper.setText(content, true);

            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
            prop.put("mail.smtp.timeout", "25000");

            // 发件人 Host是 smtp.exmail.qq.com 都要打开以下注解
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            senderImpl.setJavaMailProperties(prop);
            senderImpl.send(mailMessage);
            log.info("邮件发送成功..");
        } catch (Exception e) {
            log.debug(e.toString());
            log.info("邮件发送给" + to + "失败..");
        }
    }

    public static void main(String[] args) {
        String a559927zs = CryptUtils.encryptString("a559927z");
        System.out.println(a559927zs);

        String xx = CryptUtils.decryptString("tC0bORcpxTXWN7guiQisqNf3k5KhLbT4");
        System.out.println(xx);
        MailUtil mailUtil = new MailUtil();
        List<String> toList = Arrays.asList("jxzhang@chinahrd.net");
        String title = "修改账号密码";
        mailUtil.send(toList, title, "xxx");
    }
}
