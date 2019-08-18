package br.com.pedidovenda.interceptador;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import br.com.pedidovenda.util.Transacional;

@Transacional
@Interceptor
public class GerenciadorTransacao implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager entityManager;
	
	@AroundInvoke
	public Object executaTX(InvocationContext context) throws Exception {
		
		entityManager.getTransaction().begin();
		
		Object resultado = context.proceed();
		
		entityManager.getTransaction().commit();
		
		return resultado;
	}
}












