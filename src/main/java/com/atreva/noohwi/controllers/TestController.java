package com.atreva.noohwi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atreva.noohwi.dao.TestDAO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@RestController
@RequestMapping("/noohwi")
public class TestController {

	@Autowired
	public TestDAO testDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/index")
	public List<Integer> index() {
		List<Integer> a = new ArrayList<>();
		a.add(1);
		a.add(12);
		a.add(13);
		a.add(14);
		return a;
	}

	@GetMapping("/index2")
	public @ResponseBody void index3(HttpServletResponse response) {
		try {
			InputStream is = getClass().getResourceAsStream("/reports/report2.jrxml");
			JasperDesign design = JRXmlLoader.load(is);
			JasperReport report = JasperCompileManager.compileReport(design);

			Map<String, Object> params = new HashMap<String, Object>();

			List<Integer> a = new ArrayList<>();
			a.add(1);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(a);

			params.put("datasource", jrDataSource);

			JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, jrDataSource);

			response.setContentType("application/x-pdf");
			response.setHeader("Content-Disposition", "inline; filename=product.pdf");

			final ServletOutputStream outputStream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@GetMapping("/index1")
	public @ResponseBody void index1(HttpServletResponse response, @RequestParam Map<String, String> request) {
		try {
			response.setContentType("application/x-pdf");
			response.setHeader("Content-Disposition", "inline; filename=REPORTE.pdf");

			final ServletOutputStream outputStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(this.testDAO.getreport(Integer.parseInt(request.get("id"))),
					outputStream);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@GetMapping("/index3")
	public @ResponseBody void index4(HttpServletResponse response) {
		try {
			DataSource ds = (DataSource) this.jdbcTemplate.getDataSource();
			Connection conn = ds.getConnection();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", 1);
			InputStream is = getClass().getResourceAsStream("/reports/report1.jasper");
			System.out.println(is);

			JasperPrint jasperPrint = JasperFillManager.fillReport(is, params, conn);

			response.setContentType("application/x-pdf");
			response.setHeader("Content-Disposition", "inline; filename=product.pdf");

			final ServletOutputStream outputStream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

		} catch (Exception e) {

		}
	}

	@GetMapping("/index6")
	public @ResponseBody void index6(HttpServletResponse response) {
		try {
			DataSource ds = (DataSource) this.jdbcTemplate.getDataSource();
			Connection conn = ds.getConnection();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", 1);
			InputStream is = getClass().getResourceAsStream("/reports/report1.jasper");
			System.out.println(is);

			JasperPrint jasperPrint = JasperFillManager.fillReport(is, params, conn);

			JasperExportManager.exportReportToPdf(jasperPrint);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporter.exportReport();
//			  <iframe src="http://localhost:8080/noohwi/index6" width="600" height="400">
//			  Your browser doesn't understand IFRAME.
//			  </iframe>
//			  Download the PDF file <a href="http://localhost:8080/noohwi/index6">here</a> 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/index4")
	public @ResponseBody void index5(HttpServletResponse response) {
		try {
			DataSource ds = (DataSource) this.jdbcTemplate.getDataSource();
			Connection conn = ds.getConnection();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", 1);
			InputStream is = getClass().getResourceAsStream("/reports/report1.jasper");

			JasperPrint jasperPrint = JasperFillManager.fillReport(is, params, conn);

			response.setContentType("application/x-xls");
			response.setHeader("Content-Disposition", "inline; filename=product.xls");

			final ServletOutputStream outputStream = response.getOutputStream();

			JRXlsExporter exporter = new JRXlsExporter();

			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

			exporter.exportReport();

		} catch (Exception e) {

		}
	}
}
