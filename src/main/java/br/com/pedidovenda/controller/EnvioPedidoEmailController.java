package br.com.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import com.outjected.email.api.MailMessage;

import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.util.jsf.FacesUtil;
import br.com.pedidovenda.util.mail.Mailer;

@Named("envioEmail")
@RequestScoped
public class EnvioPedidoEmailController implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	@Inject
	private Mailer mailer;
	
	
	public void enviarPedido() {
		try {
			mailer.novaMensagem(this.pedido);
			FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
