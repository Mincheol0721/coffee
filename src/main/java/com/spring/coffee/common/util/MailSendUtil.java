package com.spring.coffee.common.util;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.coffee.member.dao.MemberDaoImpl;
import com.spring.coffee.member.vo.MemberVO;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailSendUtil {

	@Autowired
	private MemberDaoImpl memberDao;

	private final Properties serverInfo;
	private final Authenticator auth;
	private static final char[] rndAllCharacters = new char[]{
	        //number
	        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	        //uppercase
	        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
	        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
	        //lowercase
	        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
	        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	        //special symbols
	        '@', '$', '!', '%', '*', '?', '&'
	};

	public MailSendUtil() {
		serverInfo = new Properties();
		serverInfo.put("mail.smtp.host", "smtp.naver.com");
		serverInfo.put("mail.smtp.port", "465");
		serverInfo.put("mail.smtp.starttls.enable", "false");
		serverInfo.put("mail.smtp.ssl.enable", "true");
		serverInfo.put("mail.smtp.auth", "true");
		serverInfo.put("mail.smtp.debug", "true");
		serverInfo.put("mail.smtp.socketFactory.port", "465");
		serverInfo.put("mail.smtp.socketFactory.port", "465");
		serverInfo.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		serverInfo.put("mail.smtp.socketFactory.fallback", "false");

		auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("alscjf0969", "8F9PEB7ZYPD9");
			}
		};
	}

	public String getRandomPassword(int length) throws Exception {
	    SecureRandom random = new SecureRandom();
	    StringBuilder stringBuilder = new StringBuilder();

	    int rndAllCharactersLength = rndAllCharacters.length;
	    for (int i = 0; i < length; i++) {
	        stringBuilder.append(rndAllCharacters[random.nextInt(rndAllCharactersLength)]);
	    }

	    return stringBuilder.toString();
	}

	public String emailSending(String from, String to, String content) throws Exception {
		Session session = Session.getInstance(serverInfo, auth);
		session.setDebug(true);
		String tempPassword = getRandomPassword(10);
		content = (content + tempPassword);
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject("[Coffee Finder] 임시 패스워드 발급");
		msg.setText(content);

		Transport.send(msg);

		return tempPassword;
	}
}
