package br.com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.pedidovenda.model.Cliente;
import br.com.pedidovenda.model.EnderecoEntrega;
import br.com.pedidovenda.model.FormaPagamento;
import br.com.pedidovenda.model.ItemPedido;
import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.model.Produto;
import br.com.pedidovenda.model.Usuario;
import br.com.pedidovenda.repository.ClienteRepository;
import br.com.pedidovenda.repository.ProdutoRepository;
import br.com.pedidovenda.repository.UsuarioRepository;
import br.com.pedidovenda.service.CadastroPedidoService;
import br.com.pedidovenda.service.NegocioException;
import br.com.pedidovenda.util.jsf.FacesUtil;

@Named("cadastroPedido")
@ViewScoped
public class CadastroPedidoController implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Produces
	@PedidoEdicao
	private Pedido pedido;
	private String sku;
	private Produto produtoLinhaEditavel;
	private List<Usuario> vendedores;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private ClienteRepository clienteRepository;
	
	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	
	public CadastroPedidoController() {
		this.pedido = new Pedido();
		this.pedido.setEnderecoEntrega(new EnderecoEntrega());
		this.vendedores = new ArrayList<>();
	}
	
	public void inicializar() {
		if(FacesUtil.isNotPostBack()) {
			this.vendedores = this.usuarioRepository.buscaPorVendedores();
			
			this.pedido.adicionarItemVazio();
		}
	}
	
	/**
	 * @description Insere um pedido no banco de dados excluindo o item vazio da grid
	 * e o inserindo após o salvamento do pedido ou não atraves do bloco finally
	 * */
	public void salvar() {
		try {
			this.pedido.removerItemVazio();
			this.cadastroPedidoService.salvar(this.pedido);
			this.pedido = new Pedido();
			
			FacesUtil.addInfoMessage("Pedido Salvo com sucesso!");
		} catch(NegocioException e){
			FacesUtil.addErrorMessage(e.getMessage());
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
	
	public void pedidoAlterado(@Observes PedidoAlteradoEvent event) {
		this.pedido = event.getPedido();
	}
	
	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);
		
		if(this.produtoLinhaEditavel != null) {
			item.setProduto(this.produtoLinhaEditavel);
			item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
			
			this.pedido.adicionarItemVazio();
			this.produtoLinhaEditavel = null;
			
			this.pedido.recalcularValorValorTotal();
		}
	}
	
//	public void carregarProdutoPorSku() {
//		if(StringUtils.isNotEmpty(this.sku)) {
//			this.produtoLinhaEditavel = this.produtoRepository.buscaPorSKU(this.sku);
//			this.carregarProdutoLinhaEditavel();
//		}
//	}
	
	
	public void atualizarQuantidade(ItemPedido item, int linha) {
		if(item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			}else {
				this.getPedido().getItens().remove(linha);
			}
		}
		
		this.pedido.recalcularValorValorTotal();
	}
	
	public List<Produto> completarProduto(String nome){
		return this.produtoRepository.buscarPorNome(nome);
	}
	
	public void recalcularPedido() {
		this.pedido.recalcularValorValorTotal();
	}
	
	public boolean isVerificaCadastroEdicaoPedido() {
		return this.pedido.getId() == null;
	}
	
	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public List<Cliente> completarCliente(String nome){
		return this.clienteRepository.buscaClientePorNome(nome);
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}
	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public List<Usuario> getVendedores() {
		return vendedores;
	}
}
