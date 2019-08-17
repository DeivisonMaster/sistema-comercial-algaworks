package br.com.pedidovenda.util.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {
	
	
	public static void addErrorMessage(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}
	
	public static void addInfoMessage(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}
	
	public static boolean isPostBack() {
		return FacesContext.getCurrentInstance().isPostback();
	}
	
	public static boolean isNotPostBack() {
		return !isPostBack();
	}
}
