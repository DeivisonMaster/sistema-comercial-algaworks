package br.com.pedidovenda.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.model.StatusPedido;
import br.com.pedidovenda.repository.PedidoRepository;

public class CadastroPedidoService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void salvar(Pedido pedido) {
		if(pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}
		
		this.pedidoRepository.salvar(pedido);
	}

}
