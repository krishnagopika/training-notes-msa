package com.revature.revshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.revature.revshop.event.OrderEvent;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@Autowired
	private JavaMailSender mailSender;
	
	
		
	
	public void sendEmail(String to, String subject, String body) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("revshop@demomailtrap.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		
		mailSender.send(message);
	}
	
	
	
	@KafkaListener(topics="notificationTopic")
	public void getOrderEvent(OrderEvent orderEvent) {
		
		System.out.println("recieved event" +orderEvent.toString());
		
		sendEmail("krishnagopika1701@gmail.com", "Rev Shop: Order placed", orderEvent.getOrderNumber());
		
		
	}

}
