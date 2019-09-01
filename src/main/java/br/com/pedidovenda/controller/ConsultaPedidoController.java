package br.com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.model.StatusPedido;
import br.com.pedidovenda.repository.PedidoRepository;
import br.com.pedidovenda.repository.filtro.PedidoFiltro;

@Named("consultaPedido")
@ViewScoped
public class ConsultaPedidoController implements Serializable {
	private static final long serialVersionUID = 1L;

	private PedidoFiltro filtro;
	private List<Pedido> pedidosFiltrados;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	public ConsultaPedidoController() {
		this.filtro = new PedidoFiltro();
		this.pedidosFiltrados = new ArrayList<>();
	}
	
	public void pesquisar() {
		this.pedidosFiltrados = this.pedidoRepository.pedidosFiltrados(this.filtro);
	}
	
	public StatusPedido[] getStatus(){
		return StatusPedido.values();
	}
	
	public PedidoFiltro getFiltro() {
		return filtro;
	}
	public void setFiltro(PedidoFiltro filtro) {
		this.filtro = filtro;
	}
	public List<Pedido> getPedidosFiltrados() {
		this.pedidosFiltrados = this.pedidoRepository.pedidosFiltrados(this.filtro);
		
		return pedidosFiltrados;
	}
}
