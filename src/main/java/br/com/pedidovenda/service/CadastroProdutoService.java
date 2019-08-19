package br.com.pedidovenda.service;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.pedidovenda.model.Produto;
import br.com.pedidovenda.repository.ProdutoRepository;
import br.com.pedidovenda.util.Transacional;


@Dependent
public class CadastroProdutoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;
	
	
	@Transacional
	public void salvar(Produto produto) {
		Produto produtoExistente = produtoRepository.buscaPorSKU(produto);
		
		if(produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com SKU cadastrado!");
		}
		
		this.produtoRepository.salvar(produto);
	}

}
