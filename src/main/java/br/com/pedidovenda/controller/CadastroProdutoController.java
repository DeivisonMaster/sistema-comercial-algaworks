package br.com.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.pedidovenda.model.Produto;

@Named("cadastroProduto")
@ViewScoped
public class CadastroProdutoController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	
	public CadastroProdutoController() {
		this.produto = new Produto();
	}

	
	public void salvar() {
		throw new RuntimeException("Teste de exceção");
	}
	
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
