package br.com.pedidovenda.util.jsf;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.pedidovenda.service.NegocioException;

public class JSFExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapper;
	private static Log log = LogFactory.getLog(JSFExceptionHandler.class);
	
	public JSFExceptionHandler(ExceptionHandler wrapper) {
		this.wrapper = wrapper;
	}
	
	@Override
	public ExceptionHandler getWrapped() {
		return null;
	}
	
	
	/**
	 * @description método responsavel por tratar exceções lançadas pelo JSF
	 * */
	@Override
	public void handle() throws FacesException {
		
		// 1. recuperando uma coleção de eventos na fila
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();
		
		
		while(events.hasNext()) {
			
			// 2. recupera cada evento
			ExceptionQueuedEvent event = events.next(); 
			
			// 3. recupera a origem do evento ou exceção
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource(); 
			
			// 4. recupera a exceção lançada pelo evento
			Throwable exception = context.getException(); 
			
			boolean handled = false;
			NegocioException negocioException = getNegocioException(exception);
			
			try {
				
				// 5. verifica se a exceção é instancia da exceção que se quer tratar
				if(exception instanceof ViewExpiredException) {
					handled = true;
					redirect("/");
				} 
				else if(negocioException != null) {
					handled = true;
					FacesUtil.addErrorMessage(negocioException.getMessage());
				}
				else {
					handled = true;
					
					// 1 param: mensagem / 2 param: causa
					log.error("Erro de sistema: " + exception.getMessage(), exception);
					redirect("/Erro.xhtml");
				}
			}finally {
				if(handled) {
					
					// remove a exceção da lista de exceções
					events.remove();
				}
			}
			
			// finaliza o tratamento
			getWrapped().handle();
		}
	}
	
	private NegocioException getNegocioException(Throwable exception) {
		if(exception instanceof NegocioException) {
			return (NegocioException) exception;
		} else if(exception.getCause() != null) {
			return getNegocioException(exception.getCause());
		}
		return null;
	}

	private void redirect(String page) {
		try {
			
			// recupera o contexto do diretorio para redirecionamento da página
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String requestContextPath = externalContext.getRequestContextPath();
			
			externalContext.redirect(requestContextPath + page);
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}
	
}
