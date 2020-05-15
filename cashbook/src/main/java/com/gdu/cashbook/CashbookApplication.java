package com.gdu.cashbook;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @componentScan
@SpringBootApplication
@PropertySource("classpath:google.properties")
public class CashbookApplication {

	@Value("${google.username}")
	private String username;
	@Value("${google.password}")
	private String password;
	
	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setPort(587);
        javaMailSender.setUsername(this.username);
        javaMailSender.setPassword(this.password);        
        
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        javaMailSender.setJavaMailProperties(properties);          
		
		return javaMailSender;
	}
}
