package net.chinahrd.utils.eMail;

import java.util.List;

/**
 * 邮件Model
 * 
 * @author jxzhang on 2016年12月21日
 * @Verdion 1.0 版本
 */
public class MailModel {
	/** 基础配置-设置发送系统的邮箱(邮箱主机) */
	private String host;
	/** 基础配置-设置发送系统的邮箱(邮箱端口) */
	private String port;
	/** 基础配置-设置发送系统的邮箱(邮箱账号) */
	private String account;
	/** 基础配置-设置发送系统的邮箱(邮箱密码) */
	private String password;
	/**
	 * 发送对像
	 */
	private List<String> to;
	private String from;
	private String title;
	private String content;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
