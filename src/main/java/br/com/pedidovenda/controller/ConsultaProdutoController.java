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
import br.com.pedidovenda.util.jsf.FacesUtil;

@Named("consultaProduto")
@ViewScoped
public class ConsultaProdutoController implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<Produto> produtosFiltrados;
	private ProdutoFiltro filtro;
	private Produto produtoSelecionado;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	
	public ConsultaProdutoController() {
		this.filtro = new ProdutoFiltro();
	}
	
	
	public void pesquisar() { 
		this.produtosFiltrados = produtoRepository.buscaPorProdutos(filtro);
	}
	
	public void excluir() {
		this.produtoRepository.excluiProduto(this.produtoSelecionado);
		//this.produtosFiltrados.remove(this.produtoSelecionado)
		
		FacesUtil.addInfoMessage("Produto " + this.produtoSelecionado.getSku() + " excluido com sucesso!");
	}
	
	public List<Produto> getProdutosFiltrados() {
		this.produtosFiltrados = produtoRepository.buscaPorProdutos(filtro);
		
		return produtosFiltrados;
	}
	public ProdutoFiltro getFiltro() {
		return filtro;
	}
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}
	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
}
