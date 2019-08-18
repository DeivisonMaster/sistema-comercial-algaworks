package br.com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pedidovenda.model.Categoria;
import br.com.pedidovenda.model.Produto;
import br.com.pedidovenda.repository.CategoriaRepository;
import br.com.pedidovenda.service.CadastroProdutoService;
import br.com.pedidovenda.util.jsf.FacesUtil;

@Named("cadastroProduto")
@ViewScoped
public class CadastroProdutoController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private Collection<Categoria> categorias;
	private Collection<Categoria> subCategorias;
	private Categoria categoriaSelecionada;
	
	@Inject
	private CadastroProdutoService cadastroProdutoService;
	
	@Inject
	private CategoriaRepository repositoryCategoria;
	
	public CadastroProdutoController() {
		this.produto = new Produto();
	}
	
	public void inicializar() {
		System.out.println("Inicializando...");

		if(FacesUtil.isNotPostBack()) {
			categorias = repositoryCategoria.buscarPorCategorias();
		}
	}

	
	public void salvar() {
		this.cadastroProdutoService.salvar(this.produto);
		limpar();
		
		FacesUtil.addInfoMessage("Produto salvo com sucesso!");
	}
	
	private void limpar() {
		this.produto = new Produto();
		this.categoriaSelecionada = null; 
		this.subCategorias = new ArrayList<>();
	}
	
	public void carregarSubCategorias() {
		this.subCategorias = this.repositoryCategoria.buscaPorSubCategoriaDeCategoria(this.categoriaSelecionada);
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
	
	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}
	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}
	public Collection<Categoria> getSubCategorias() {
		return subCategorias;
	}
}
