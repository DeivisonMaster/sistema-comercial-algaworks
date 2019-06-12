import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pedidovenda.model.Categoria;
import br.com.pedidovenda.model.Cliente;
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
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PedidoVenda");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		
		entityManager.getTransaction().begin();
		
		Cliente cliente = entityManager.find(Cliente.class, 7L);
		Usuario vendedor = entityManager.find(Usuario.class, 10L);
		Produto produto = entityManager.find(Produto.class, 23L);
		
		EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
		enderecoEntrega.setLogradouro("Rua dos Mercados");
		enderecoEntrega.setNumero("1000");
		enderecoEntrega.setCidade("Uberlandia");
		enderecoEntrega.setUf("MG");
		enderecoEntrega.setCep("78158-990");
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente );
		pedido.setDataCriacao(new Date());
		pedido.setDataEntrega(new Date());
		pedido.setFormaPagamento(FormaPagamento.CARTAO_CREDITO);
		pedido.setObservacao("aberto das 08 as 18h");
		pedido.setStatus(StatusPedido.ORCAMENTO);
		pedido.setValorDesconto(BigDecimal.ZERO);
		pedido.setValorFrete(BigDecimal.ZERO);
		pedido.setValorTotal(BigDecimal.ZERO);
		pedido.setVendedor(vendedor);
		pedido.setEnderecoEntrega(enderecoEntrega);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(10);
		itemPedido.setValorUnitario(new BigDecimal(2.32));
		itemPedido.setPedido(pedido);
		
		pedido.getItens().add(itemPedido);
		
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
	}
}

















