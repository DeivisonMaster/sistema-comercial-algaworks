package br.com.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.service.EmissaoPedidoService;
import br.com.pedidovenda.service.NegocioException;
import br.com.pedidovenda.util.jsf.FacesUtil;

@Named("emissaoPedido")
@ViewScoped
public class EmissaoPedidoController implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	@Inject
	private EmissaoPedidoService emissaoPedidoService;
	
	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	
	
	public void emitirPedido() {
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.emissaoPedidoService.emitir(this.pedido);
			this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(this.pedido));
			
			
			FacesUtil.addInfoMessage("Pedido emitido com sucesso!");
			
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
	
}
