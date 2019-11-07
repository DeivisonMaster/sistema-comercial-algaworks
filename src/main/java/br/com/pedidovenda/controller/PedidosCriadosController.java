package br.com.pedidovenda.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.pedidovenda.model.Usuario;
import br.com.pedidovenda.repository.PedidoRepository;
import br.com.pedidovenda.security.UsuarioLogado;
import br.com.pedidovenda.security.UsuarioSistema;

@Named
@RequestScoped
public class PedidosCriadosController {
	
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

	private LineChartModel model;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioLogado;
	
	public void preRender() {
		this.model = new LineChartModel();
		
		adicionaSerie("Todos os pedidos", null);
		adicionaSerie("Meus Pedidos", usuarioLogado.getUsuario());
	}
	
	private void adicionaSerie(String rotulo) {
		LineChartSeries series = new LineChartSeries(rotulo);
		
		series.set(10, Math.random() * 1000);
		series.set(20, Math.random() * 900);
		series.set(30, Math.random() * 800);
		series.set(40, Math.random() * 700);
		series.set(50, Math.random() * 600);
		series.set(60, Math.random() * 600);
		
		this.model.addSeries(series);
	}
	
	private void adicionaSerie(String rotulo, Usuario criadoPor) {
		Map<Date, BigDecimal> valoresPorData = this.pedidoRepository.valoresTotaisPorData(15, criadoPor);
		
		LineChartSeries series = new LineChartSeries(rotulo);
		
		for (Date data : valoresPorData.keySet()) {
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}
		
		this.model.addSeries(series);
	}

	public CartesianChartModel getModel() {
		return model;
	}
}
