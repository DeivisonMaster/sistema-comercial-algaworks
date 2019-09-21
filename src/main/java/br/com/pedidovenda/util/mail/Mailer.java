package br.com.pedidovenda.util.mail;

import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.outjected.email.impl.templating.velocity.VelocityTemplate;

import br.com.pedidovenda.model.Pedido;

@RequestScoped
public class Mailer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String emailRemetente = "x";
	private String senhaRemetente = "x";

	public void montarConfigEnvioEmail(Properties props) {
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	public Session autenticacaoServidorDeEmail(Properties props, String emailRemetente, String senhaRemetente) {
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailRemetente, senhaRemetente);
			}
		});
		session.setDebug(true);
		
		return session;
	}

	public void novaMensagem(Pedido pedido) throws MessagingException {
		Properties props = new Properties();
		montarConfigEnvioEmail(props);
		
		Session session = autenticacaoServidorDeEmail(props, emailRemetente, senhaRemetente);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("sac@pedidosonline.com.br")); // remetente

		Address[] toUser = InternetAddress.parse(pedido.getCliente().getEmail());
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject("Pedido " + pedido.getId());
//		message.setContent(
//				new VelocityTemplate(getClass().getResourceAsStream("/emails/email.template")), "text/html; charset=UTF-8");
//		message.put("pedido", pedido);
	    //message.put("number", new NumberTool());
		//message.put("locale", new Locale("pt", "BR"));
		message.setText("Olá " + pedido.getCliente().getNome() + ", Seu e-mail foi cadastrado com sucesso, em breve novidades.");
		Transport.send(message);

	}

}
