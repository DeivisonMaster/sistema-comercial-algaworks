package br.com.pedidovenda.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.pedidovenda.util.report.UtilRelatorio;

@Named("relatorioPedido")
@RequestScoped
public class RelatorioPedidoController {
	
	private Date dataInicio;
	private Date dataFim;
	
	@Inject
	private FacesContext context;
	
	@Inject
	private ExternalContext externalContext;
	
	private HttpServletResponse response;
	
	@Inject
	private EntityManager entityManager;
	
	private String caminhoRelatorio = "/relatorios/rel_pedido.jasper";
	//private String caminhoRelatorio = "D:\\_workspace\\desenvolvimento\\PedidoVenda\\teste\\rel_pedido.jasper";
	private Map<String, Object> parametros = new HashMap<>();
	
	public void emitir() {
		this.response = (HttpServletResponse) this.externalContext.getResponse();
		
		this.parametros.put("data_inicio", this.dataInicio);
		this.parametros.put("data_fim", this.dataFim);

		UtilRelatorio utilRelatorio = new UtilRelatorio(caminhoRelatorio, response,
				parametros, "Pedidos Emitidos.pdf");
		
		Session session = this.entityManager.unwrap(Session.class);
		session.doWork(utilRelatorio);
		
		context.responseComplete();
	}
	
	
	
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	
	
	
}
