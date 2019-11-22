package br.com.pedidovenda.util.mail;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

public class MailConfigProducer {
	
	
	@Produces
	@RequestScoped
	public SessionConfig getMailConfig() throws IOException {
		SimpleMailConfig config = new SimpleMailConfig();
		
		//Properties properties = new Properties();
		//properties.load(getClass().getResourceAsStream("/mail.properties"));
		
		config.setServerHost("smtp.gmail.com");
		config.setServerPort(465);
		config.setEnableSsl(Boolean.parseBoolean("javax.net.ssl.SSLSocketFactory"));
		config.setAuth(Boolean.parseBoolean("true"));
		config.setUsername("");
		config.setPassword("");
		
		return config;
	}
}
