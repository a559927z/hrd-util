package net.chinahrd.mail;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.chinahrd.utils.eMail.MailUtil;

public class EMailTest {

	@Autowired
	private MailUtil mailUtil;
	
	
	@Ignore
	@Test
	public void sendTest() {
		// MailModel model = new MailModel();
		// model.setHost("smtp.163.com");
		// model.setPort("25");
		// model.setAccount("a559927z@163.com");
		// model.setPassword("163Admin");
		// model.setFrom("a559927z@163.com");

		// model.setHost("smtp.exmail.qq.com");
		// model.setPort("465");
		// model.setAccount("jxzhang@chinahrd.net");
		// model.setPassword("lk0SexOnTOReEM7a");
		// model.setFrom("jxzhang@chinahrd.net");

		// model.setHost("smtp.qq.com");
		// model.setPort("465");
		// model.setAccount("kenzhang_sing@qq.com");
		// model.setPassword("jgqpcqlbzzjmcaje");
		// model.setFrom("61361408@qq.com");

		// List<String> rs = Arrays.asList("61361408@qq.com");
		// model.setTo(rs);
		// MailUtil.send(model);

		List<String> rs = Arrays.asList("bliang@chinahrd.net", "dcli@chinahrd.net", "ddli@chinahrd.net",
				"gjzou@chinahrd.net", "glyang@chinahrd.net", "htpeng@chinahrd.net", "jayu@chinahrd.net",
				// "jpeng@chinahrd.net",
				"jxzhang@chinahrd.net", "kyin@chinahrd.net", "lliu@chinahrd.net", "llu@chinahrd.net",
				"lma@chinahrd.net", "nxu@chinahrd.net", "qpzhu@chinahrd.net", "shzeng@chinahrd.net",
				"syliang@chinahrd.net", "wqcai@chinahrd.net", "wxiao@chinahrd.net", "xlcheng_gz@chinahrd.net",
				"xwli@chinahrd.net", "yjhuang@chinahrd.net", "yjtian@chinahrd.net", "yxtao@chinahrd.net",
				// "zbquan@chinahrd.net",
				"zfzeng@chinahrd.net", "zlwu@chinahrd.net", "zpwang@chinahrd.net",
				// "hjli@chinahrd.net",
				// "ntian@chinahrd.net","wxwang@chinahrd.net",
				"xlyang@chinahrd.net", "zwliang@chinahrd.net", "cliu@chinahrd.net", "ychen@chinahrd.net",
				"jqding@chinahrd.net", "82178582@qq.com");

		List<String> toList = rs;
		String title = "测试邮件，不必理会。";
		String content = telmplet();
		mailUtil.send(toList, title, content);

	}

	private String telmplet() {
		StringBuffer buf = new StringBuffer();
		buf.append("<HTML><head></head><body>");
		buf.append("<p><h1>测试邮件，不必理会。</h1></p>");
		buf.append("<p>");
		buf.append("    &nbsp;大家好！<br/>");
		buf.append("</p>");
		buf.append("<div>");
		buf.append("    <br/>考勤调整期即将截止（终止期限为每月7日），为避免影响个人的薪酬核算，请各位速速上考勤系统核对上月的考勤数据。2");
		buf.append("</div>");
		buf.append("</body></html>");
		return buf.toString();
	}
}
