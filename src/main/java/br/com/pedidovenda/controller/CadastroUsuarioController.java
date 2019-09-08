package br.com.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pedidovenda.model.Usuario;
import br.com.pedidovenda.repository.UsuarioRepository;
import br.com.pedidovenda.util.jsf.FacesUtil;

@Named("cadastroUsuario")
@ViewScoped
public class CadastroUsuarioController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	
	
	public CadastroUsuarioController() {
		this.usuario = new Usuario();
	}
	
	public void cadastrar() {
		this.usuarioRepository.salva(this.usuario);
		this.usuario = new Usuario();
		
		FacesUtil.addInfoMessage("Cadastro realizado com sucesso!");
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
