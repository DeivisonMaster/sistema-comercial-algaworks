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

public class JSFExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapper;
	
	public JSFExceptionHandler(ExceptionHandler wrapper) {
		this.wrapper = wrapper;
	}
	
	@Override
	public ExceptionHandler getWrapped() {
		return null;
	}
	
	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();
		
		while(events.hasNext()) {
			ExceptionQueuedEvent event = events.next(); // recupera o evento
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource(); // recupera a origem do evento
			
			Throwable exception = context.getException(); // recupera a exceção lançada pelo evento
			
			try {
				if(exception instanceof ViewExpiredException) {
					redirect("/");
				}
			}finally {
				events.remove();
			}
			
			getWrapped().handle();
		}
	}

	private void redirect(String page) {
		try {
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
