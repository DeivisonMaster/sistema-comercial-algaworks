package br.com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.pedidovenda.model.EnderecoEntrega;
import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.service.NegocioException;

@Named("cadastroPedido")
@ViewScoped
public class CadastroPedidoController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<Integer> itens;
	private Pedido pedido;
	
	
	public CadastroPedidoController() {
		this.pedido = new Pedido();
		this.pedido.setEnderecoEntrega(new EnderecoEntrega());
		this.itens = new ArrayList<>();
		this.itens.add(1);
	}
	
	
	public void salvar() {
		//throw new NegocioException("Pedido não pode ser salvo");
	}
	
	public List<Integer> getItens() {
		return itens;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
}
