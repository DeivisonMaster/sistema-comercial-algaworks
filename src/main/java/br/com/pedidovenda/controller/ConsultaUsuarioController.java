package br.com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pedidovenda.model.Usuario;
import br.com.pedidovenda.repository.UsuarioRepository;

@Named("consultaUsuario")
@ViewScoped
public class ConsultaUsuarioController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Usuario> usuarios;
	
	private Usuario usuarioSelecionado;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	public ConsultaUsuarioController() {
		this.usuarios = new ArrayList<Usuario>();
	}
	
	
	public void pesquisar() {
		System.out.println("pesquisar...");
	}
	
	public void excluirUsuario() {
		this.usuarioRepository.excluirUsuario(this.usuarioSelecionado);
	}
	
	public List<Usuario> getUsuarios() {
		this.usuarios = this.usuarioRepository.buscaPorVendedores();
		
		return usuarios;
	}
	
	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}
	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}
