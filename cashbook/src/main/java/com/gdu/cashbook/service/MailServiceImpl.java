package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mail.MailHandler;



@Service
public class MailServiceImpl implements MailService{

	@Autowired
	private JavaMailSender sender;

	public Map<String, Object> send(String email, String title, String body) {

		MailHandler mail;
		try {
			mail = new MailHandler(sender);
			mail.setFrom("no-reply@norepy.com", "비밀번호 찾기");
			mail.setTo(email);
			mail.setSubject(title);
			mail.setText(body);
			mail.send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String resultCode = "S-1";
		String msg = "메일이 발송되었습니다.";
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("resultCode", resultCode);
		r.put("msg", msg);
		return r;
	}

}
