package br.com.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.pedidovenda.model.Cliente;
import br.com.pedidovenda.util.JPAUtil;

public class ClienteRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public ClienteRepository() {
		this.entityManager = new JPAUtil().getEntityManager();
	}
	
	public Cliente buscaPorId(Long id) {
		return this.entityManager.find(Cliente.class, id);
	}
	
	public List<Cliente> buscaClientePorNome(String nome){
		TypedQuery<Cliente> query = this.entityManager.createQuery("from Cliente Where upper(nome) like :pNome", Cliente.class);
		query.setParameter("pNome", nome.toUpperCase() + "%");
		List<Cliente> clientePesquisado = query.getResultList();
		
		return clientePesquisado;
	}
}
