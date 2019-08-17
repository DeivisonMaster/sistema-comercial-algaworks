package br.com.pedidovenda.service;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.pedidovenda.model.Produto;
import br.com.pedidovenda.repository.ProdutoRepository;


@Dependent
public class CadastroProdutoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;
	
	
	public void salvar(Produto produto) {
		this.produtoRepository.salvar(produto);
	}

}
