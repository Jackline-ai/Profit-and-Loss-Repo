package com.profitandloss.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.profitandloss.model.ProfitandLossApi;
import com.profitandloss.utils.Utilities;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
@Service
public class ProfitandLossService {
    	
      
	public String exportReport(String reportFormat,ProfitandLossApi profitandLossApi) throws FileNotFoundException, JRException {

		String path = "/home/jackline/Desktop/Reports/";

		// Load the report template

		List<ProfitandLossApi> profitandLossApis = new ArrayList<ProfitandLossApi>();
		profitandLossApis.add(profitandLossApi);

		File file = ResourceUtils.getFile("classpath:Profit_and_Loss.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(profitandLossApis);
		Map<String, Object> parameters = new HashMap<>();
		

		JRBeanCollectionDataSource dataSourceHeader = new JRBeanCollectionDataSource(profitandLossApis);
		parameters.put("HEADER_DATASOURCE", dataSourceHeader);
		
		JRBeanCollectionDataSource dataSourceTransactionIncome = new JRBeanCollectionDataSource(profitandLossApis);
		parameters.put("INCOME_DATASOURCE", dataSourceTransactionIncome);
		
		JRBeanCollectionDataSource dataSourceTransactionExpenses = new JRBeanCollectionDataSource(profitandLossApis);
		parameters.put("EXPENSES_DATASOURCE", dataSourceTransactionExpenses);
		
		
		
		parameters.put("createdBy", "Jackline");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		if (reportFormat.equalsIgnoreCase("xml")) {
			return JasperExportManager.exportReportToXml(jasperPrint);

		}

		else if (reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + Utilities.getCurrentDateTime());

		}

		return "report generated in path: " + path;
	}


}


