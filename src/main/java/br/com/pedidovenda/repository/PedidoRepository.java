package br.com.pedidovenda.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import br.com.pedidovenda.model.DataValor;
import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.model.Usuario;
import br.com.pedidovenda.repository.filtro.PedidoFiltro;
import br.com.pedidovenda.util.JPAUtil;

public class PedidoRepository implements Serializable{
	private static final long serialVersionUID = 1L; 
	
	@Inject
	private EntityManager entityManager;
	
	public PedidoRepository() {
		this.entityManager = new JPAUtil().getEntityManager();
	}
	
	public Map<Date, BigDecimal> valoresTotaisPorData(
			Integer numeroDias, Usuario criadoPor){
		
		Session session = this.entityManager.unwrap(Session.class);
		
		numeroDias -= 1;
		
		Calendar dataInicial = Calendar.getInstance();
		System.out.println(dataInicial);

		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		System.out.println(dataInicial);
		
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDias * -1); // recupera data retroativa (dia atual - dia parametro)
		
		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDias, dataInicial);
		
		/**
		 * select date(data_criacao) as data, sum(valor_total) as valor
		 * from pedido where data_criacao >= :dataInicial and vendedor_id = :criadoPor
		 * group by date(data_criacao)
		 * 
		 * **/
		Criteria criteria = session.createCriteria(Pedido.class);
		
		criteria.setProjection(Projections.projectionList()
			.add(Projections.sqlGroupProjection(
					"date(data_cricao, as data", 
					" date(data_cricao)", new String[] {"data"},
					new Type[] { StandardBasicTypes.DATE}))
				.add(Projections.sum("valorTotal").as("valor"))
			)
				.add(Restrictions.ge("dataCriacao", dataInicial.getTime())
			);
		
		if(criadoPor != null) {
			criteria.add(Restrictions.eq("vendedor", criadoPor));
		}
		
		List<DataValor> valoresPorData = 
				criteria.setResultTransformer(Transformers.aliasToBean(DataValor.class)).list();
		
		for(DataValor dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}
		
		return resultado;
	}
	
	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDias, Calendar dataInicial) {
		Map<Date, BigDecimal>  mapaInicial = new TreeMap<>();
		
		dataInicial = (Calendar) dataInicial.clone();
		
		for (int i = 0; i <= numeroDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return mapaInicial;
	}
	
	public List<Pedido> pedidosFiltrados(PedidoFiltro filtro){
		Session session = this.entityManager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(Pedido.class);
		criteria.createAlias("cliente", "c");
		criteria.createAlias("vendedor", "v");
		
		
		if(filtro.getNumeroDe() != null) {
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}
		if(filtro.getNumeroAte() != null) {
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}
		if(filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}
		if(filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}
		if(StringUtils.isNotBlank(filtro.getNomeCliente())) {
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendedor(), MatchMode.ANYWHERE));
		}
		if(filtro.getStatus() != null && filtro.getStatus().length > 0 ) {
			criteria.add(Restrictions.in("status", filtro.getStatus()));
		}
		
		return criteria.addOrder(Order.asc("id")).list();
		
	}

	public Pedido salvar(Pedido pedido) {
		this.entityManager.getTransaction().begin();
		Pedido pedidoSalvo = this.entityManager.merge(pedido);
		this.entityManager.getTransaction().commit();
		
		return pedidoSalvo;
	}

	public Pedido buscaPorId(Long id) {
		return this.entityManager.find(Pedido.class, id);
	}
}
