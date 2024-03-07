package com.profitandloss.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profitandloss.model.ProfitandLossApi;
import com.profitandloss.model.ApiResponse;
import com.profitandloss.service.ProfitandLossService;

import net.sf.jasperreports.engine.JRException;
@RestController
@RequestMapping("/api")
public class ProfitandLossController {
	@Autowired
	private ProfitandLossService profitandLossService;
	
	@PostMapping("/report")
	public ResponseEntity<ApiResponse> saveEtimsData(@RequestBody ProfitandLossApi profitandLossApi) {
		ApiResponse response = new ApiResponse("Invoice printed successfully", 200);
		
		try {
			profitandLossService.exportReport("pdf", profitandLossApi);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}
}
