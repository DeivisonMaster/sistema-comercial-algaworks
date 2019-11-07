import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import br.com.pedidovenda.model.Categoria;
import br.com.pedidovenda.model.Cliente;
import br.com.pedidovenda.model.DataValor;
import br.com.pedidovenda.model.Endereco;
import br.com.pedidovenda.model.EnderecoEntrega;
import br.com.pedidovenda.model.FormaPagamento;
import br.com.pedidovenda.model.Grupo;
import br.com.pedidovenda.model.ItemPedido;
import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.model.Produto;
import br.com.pedidovenda.model.StatusPedido;
import br.com.pedidovenda.model.TipoPessoa;
import br.com.pedidovenda.model.Usuario;

public class Teste {
	
	public static Map<Date, BigDecimal> valoresTotaisPorData(
			Integer numeroDias, Usuario criadoPor, Session session){
		
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
	
	private static Map<Date, BigDecimal> criarMapaVazio(Integer numeroDias, Calendar dataInicial) {
		Map<Date, BigDecimal>  mapaInicial = new TreeMap<>();
		
		dataInicial = (Calendar) dataInicial.clone();
		
		for (int i = 0; i <= numeroDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return mapaInicial;
	}

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PedidoVenda");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Session session = entityManager.unwrap(Session.class);
		
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		
		Map<Date, BigDecimal> valores = valoresTotaisPorData(6, usuario, session);
		
		for (Date data : valores.keySet()) {
			System.out.println(data + " = " + valores.get(data));
		}
		
		entityManager.close();
		
		
		
//		entityManager.getTransaction().begin();
		
//		Cliente cliente = entityManager.find(Cliente.class, 7L);
//		Usuario vendedor = entityManager.find(Usuario.class, 10L);
//		Produto produto = entityManager.find(Produto.class, 23L);
//		
//		EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
//		enderecoEntrega.setLogradouro("Rua dos Mercados");
//		enderecoEntrega.setNumero("1000");
//		enderecoEntrega.setCidade("Uberlandia");
//		enderecoEntrega.setUf("MG");
//		enderecoEntrega.setCep("78158-990");
//		
//		Pedido pedido = new Pedido();
//		pedido.setCliente(cliente );
//		pedido.setDataCriacao(new Date());
//		pedido.setDataEntrega(new Date());
//		pedido.setFormaPagamento(FormaPagamento.CARTAO_CREDITO);
//		pedido.setObservacao("aberto das 08 as 18h");
//		pedido.setStatus(StatusPedido.ORCAMENTO);
//		pedido.setValorDesconto(BigDecimal.ZERO);
//		pedido.setValorFrete(BigDecimal.ZERO);
//		pedido.setValorTotal(BigDecimal.ZERO);
//		pedido.setVendedor(vendedor);
//		pedido.setEnderecoEntrega(enderecoEntrega);
//		
//		ItemPedido itemPedido = new ItemPedido();
//		itemPedido.setProduto(produto);
//		itemPedido.setQuantidade(10);
//		itemPedido.setValorUnitario(new BigDecimal(2.32));
//		itemPedido.setPedido(pedido);
//		
//		pedido.getItens().add(itemPedido);
//		
//		entityManager.persist(pedido);
//		entityManager.getTransaction().commit();
		
	}
}

















