package br.com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pedidovenda.model.Produto;
import br.com.pedidovenda.repository.ProdutoRepository;
import br.com.pedidovenda.repository.filtro.ProdutoFiltro;

@Named("consultaProduto")
@ViewScoped
public class ConsultaProdutoController implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<Produto> produtosFiltrados;
	private ProdutoFiltro filtro;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	
	public ConsultaProdutoController() {
		this.filtro = new ProdutoFiltro();
	}
	
	
	public void pesquisar() { 
		this.produtosFiltrados = produtoRepository.buscaPorProdutos(filtro);
	}
	
	public List<Produto> getProdutosFiltrados() {
		this.produtosFiltrados = produtoRepository.buscaPorProdutos(filtro);
		
		return produtosFiltrados;
	}
	public ProdutoFiltro getFiltro() {
		return filtro;
	}
}
