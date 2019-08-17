package br.com.pedidovenda.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pedidovenda.model.Categoria;
import br.com.pedidovenda.model.Produto;

@Named("cadastroProduto")
@ViewScoped
public class CadastroProdutoController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private Collection<Categoria> categorias;
	
	@Inject
	private EntityManager entityManager;
	
	
	
	public CadastroProdutoController() {
		this.produto = new Produto();
	}
	
	public void inicializar() {
		
		
		this.categorias = entityManager.createQuery("from Categoria",  Categoria.class).getResultList();
		entityManager.close();
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
	public Collection<Categoria> getCategorias() {
		return categorias;
	}
}
