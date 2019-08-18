package br.com.pedidovenda.repository;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.pedidovenda.model.Produto;

@Dependent
public class ProdutoRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;

	
	public void salvar(Produto produto) {
		this.entityManager.persist(produto);
	}

	public Produto buscaPorSKU(Produto produto) {
		try {
			return this.entityManager.createQuery("FROM Produto WHERE UPPER(sku) = :sku", Produto.class)
					.setParameter("sku", produto.getSku().toUpperCase())
					.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
		
	}

}
