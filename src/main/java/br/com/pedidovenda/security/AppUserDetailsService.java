package br.com.pedidovenda.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.pedidovenda.model.Grupo;
import br.com.pedidovenda.model.Usuario;
import br.com.pedidovenda.repository.UsuarioRepository;
import br.com.pedidovenda.util.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UsuarioRepository usuarioRepository = new UsuarioRepository(); //CDIServiceLocator.getBean(UsuarioRepository.class);
		Usuario usuario = usuarioRepository.buscaPorEmail(email);
		
		UsuarioSistema usuarioSistema = null;
		
		if(usuario != null) {
			usuarioSistema = new UsuarioSistema(usuario, getGruposDo(usuario));
		}
		
		return usuarioSistema;
	}

	private Collection<? extends GrantedAuthority> getGruposDo(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		for (Grupo grupo : usuario.getGrupos()) {
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
		}
		
		return authorities;
	}
	
	
}
