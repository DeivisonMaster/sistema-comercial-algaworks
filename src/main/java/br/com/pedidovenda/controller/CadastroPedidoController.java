package br.com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pedidovenda.model.Cliente;
import br.com.pedidovenda.model.EnderecoEntrega;
import br.com.pedidovenda.model.FormaPagamento;
import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.model.Usuario;
import br.com.pedidovenda.repository.ClienteRepository;
import br.com.pedidovenda.repository.UsuarioRepository;
import br.com.pedidovenda.service.CadastroPedidoService;
import br.com.pedidovenda.service.NegocioException;
import br.com.pedidovenda.util.jsf.FacesUtil;

@Named("cadastroPedido")
@ViewScoped
public class CadastroPedidoController implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Pedido pedido;
	private List<Usuario> vendedores;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private ClienteRepository clienteRepository;
	
	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	
	public CadastroPedidoController() {
		this.pedido = new Pedido();
		this.pedido.setEnderecoEntrega(new EnderecoEntrega());
		this.vendedores = new ArrayList<>();
	}
	
	public void inicializar() {
		if(FacesUtil.isNotPostBack()) {
			this.vendedores = this.usuarioRepository.buscaPorVendedores();
		}
	}
	
	public void salvar() {
		this.cadastroPedidoService.salvar(this.pedido);
		
		FacesUtil.addInfoMessage("Pedido Salvo com sucesso!");
	}
	
	public boolean isVerificaCadastroEdicaoPedido() {
		return this.pedido.getId() == null;
	}
	
	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}
	
	public List<Cliente> completarCliente(String nome){
		return this.clienteRepository.buscaClientePorNome(nome);
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public List<Usuario> getVendedores() {
		return vendedores;
	}
}
