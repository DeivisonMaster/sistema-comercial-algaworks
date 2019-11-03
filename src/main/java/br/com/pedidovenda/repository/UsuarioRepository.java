package br.com.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.pedidovenda.model.Usuario;
import br.com.pedidovenda.util.JPAUtil;

public class UsuarioRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//@Inject
	private EntityManager entityManager;
	
	public UsuarioRepository() {
		this.entityManager = new JPAUtil().getEntityManager();
	}
	
	
	public Usuario buscaPorId(Long id) {
		return this.entityManager.find(Usuario.class, id);
	}
	
	public List<Usuario> buscaPorVendedores(){
		// TODO filtrar apenas vendedores por um grupo especifico
		TypedQuery<Usuario> usuario = this.entityManager.createQuery("from Usuario", Usuario.class);
		List<Usuario> usuarioPesquisado = usuario.getResultList();
		
		return usuarioPesquisado;
		
	}


	public void excluirUsuario(Usuario usuarioSelecionado) {
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(usuarioSelecionado);
		this.entityManager.getTransaction().commit();
	}


	public void salva(Usuario usuario) {
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(usuario);
		this.entityManager.getTransaction().commit();
		
	}


	public Usuario buscaPorEmail(String email) {
		Usuario usuario = null;
		
		try {
			TypedQuery<Usuario> query = this.entityManager.createQuery("select u from Usuario u where u.email = :email", Usuario.class);
			query.setParameter("email", email.toLowerCase());
			
			usuario = query.getSingleResult();
			
		} catch (NoResultException e) {
			// Nenhum usuário encontrado
		}
		
		return usuario;
	}
	
}
