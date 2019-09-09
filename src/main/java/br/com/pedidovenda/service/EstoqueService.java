package br.com.pedidovenda.service;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.pedidovenda.model.ItemPedido;
import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.repository.PedidoRepository;

@Dependent
public class EstoqueService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoRepository pedidoRepository;

	public void baixarItensEstoque(Pedido pedido) {
		pedido = this.pedidoRepository.buscaPorId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

}
