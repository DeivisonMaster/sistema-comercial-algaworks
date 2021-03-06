package br.com.pedidovenda.security;

import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.pedidovenda.model.Usuario;

@Dependent
public class UsuarioSistema extends User{
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public UsuarioSistema(Usuario usuario, 
			Collection<? extends GrantedAuthority> authorities) {
		
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
