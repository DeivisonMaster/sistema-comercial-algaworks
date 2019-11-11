package br.com.pedidovenda.util.report;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class UtilRelatorio implements Work{
	
	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;
	private InputStream relatorioStream;
	
	
	public UtilRelatorio(String caminhoRelatorio, HttpServletResponse response, Map<String, Object> parametros,
			String nomeArquivoSaida) {
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;
		//this.relatorioStream = this.getClass().getResourceAsStream(caminhoRelatorio);
	}


	@Override
	public void execute(Connection connection) throws SQLException {
		try {
			relatorioStream = this.getClass().getResourceAsStream(caminhoRelatorio);
			
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, connection);
			
			JRExporter exportador = new JRPdfExporter();
			exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, this.response.getOutputStream());
			exportador.setParameter(JRExporterParameter.JASPER_PRINT, print);
			
			this.response.setContentType("application/pdf");
			this.response.setHeader("Content-disposition", "inline; filename=" + this.nomeArquivoSaida);
			exportador.exportReport();
			
			
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
//			JasperReport report = (JasperReport) JRLoader.loadObject(relatorioStream);
//			Properties info = connection.getClientInfo();
//			
//			JasperPrint print = JasperFillManager.fillReport(report, parametros, getConnection());
//			JasperExportManager.exportReportToPdfStream(print, baos);
//			
//			response.reset();
//			response.setContentType("application/pdf");
//			response.setContentLength(baos.size());
//			
//			response.setHeader("Context-disposition", "inline; filename=teste.pdf");
//			response.getOutputStream().write(baos.toByteArray());
//			
//			response.getOutputStream().flush();
//			response.getOutputStream().close();
			

		} catch (Exception e) {
			throw new SQLException("Erro ao executar relatório " + this.caminhoRelatorio, e);
		}
	}
	
	
	public Connection getConnection() {
		Connection conexao = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/pdv", "root", "");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conexao;
	}
	
}






















