package com.atreva.noohwi.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

@Repository
public class TestDAOImpl implements TestDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void uno() {
		String CREATE = "INSERT INTO cw.states (id, descripcion, country_id, code_curp, iso3) VALUES (?, ?, ?, ?, ?)";
		int a = this.jdbcTemplate.update(CREATE, 90, "asd", 484, "AS", "ASD");
		if (a == 1) {
			System.out.println("ok");
		} else {
			System.out.println("no");
		}
	}

	@Override
	public byte[] reporte1(Integer id) {
		System.out.println("ID A IMPRIMIR: " + id);

		byte[] bytesReporte = null;
		try {

			DataSource ds = (DataSource) this.jdbcTemplate.getDataSource();
			Connection conn = ds.getConnection();
			Map params = new HashMap();
			params.put("id", id);
			InputStream is = getClass().getResourceAsStream("/reports/report1.jasper");
			System.out.println(is);
			bytesReporte = JasperRunManager.runReportToPdf(is, params, conn);

		} catch (JRException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bytesReporte;
	}

	@Override
	public JasperPrint getreport(Integer id) {
		DataSource ds = (DataSource) this.jdbcTemplate.getDataSource();
		Connection conn = null;
		JasperPrint jasperPrint = null;
		try {
			conn = ds.getConnection();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			InputStream is = getClass().getResourceAsStream("/reports/report1.jasper");
			System.out.println(is);

			jasperPrint = JasperFillManager.fillReport(is, params, conn);
		} catch (SQLException | JRException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return jasperPrint;
	}

}
