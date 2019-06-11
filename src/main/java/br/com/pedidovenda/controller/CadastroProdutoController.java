package br.com.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CadastroProdutoController implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	public void salvar() {
		throw new RuntimeException("Teste de exceção");
	}
}
