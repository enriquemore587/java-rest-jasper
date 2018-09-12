package com.atreva.noohwi.dao;

import net.sf.jasperreports.engine.JasperPrint;

public interface TestDAO {
	public abstract void uno();
	public abstract byte[] reporte1(Integer id);
	public abstract JasperPrint getreport(Integer id);
}
