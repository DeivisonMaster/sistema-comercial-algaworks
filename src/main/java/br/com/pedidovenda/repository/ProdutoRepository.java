package br.com.pedidovenda.repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pedidovenda.model.Produto;
import br.com.pedidovenda.util.JPAUtil;

@Dependent
public class ProdutoRepository {

	@Inject
	private EntityManager entityManager;

//	public ProdutoRepository() {
//		this.entityManager = new JPAUtil().getEntityManager();
//	}

	public void salvar(Produto produto) {
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(produto);
		this.entityManager.getTransaction().commit();
	}

}
