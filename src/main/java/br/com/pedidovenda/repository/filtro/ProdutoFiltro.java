package br.com.pedidovenda.repository.filtro;

import java.io.Serializable;

public class ProdutoFiltro implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sku;
	private String nome;
	
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
