package com.celinamax.easyfood.services;

import org.springframework.mail.SimpleMailMessage;

import com.celinamax.easyfood.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
