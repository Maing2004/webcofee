package com.example.ZVnMobile.api.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ZVnMobile.dto.KpiDto;
import com.example.ZVnMobile.dto.ReportYearDto;
import com.example.ZVnMobile.service.impl.IKpiService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/kpi")
public class AdminKpiApi {
	
	@Autowired
	private IKpiService iKpiService;
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(iKpiService.findAllKpi(), HttpStatus.OK);
	}
	
	@GetMapping("/get-by-search")
	public ResponseEntity<?> getBySearch(
			@RequestParam("year") Integer year,
			@RequestParam("month") Integer month,
			@RequestParam("type") String type){
		return new ResponseEntity<>(iKpiService.getBySearch(year, month, type), HttpStatus.OK);
	}
	
	@GetMapping("/insert")
	public ResponseEntity<?> insert(
			@RequestBody KpiDto kpi){
		return new ResponseEntity<>(iKpiService.insertKpi(kpi), HttpStatus.OK);
	}
	
	@GetMapping("/update")
	public ResponseEntity<?> update(
			@RequestBody KpiDto kpi){
		return new ResponseEntity<>(iKpiService.updateKpi(kpi), HttpStatus.OK);
	}
	
	@GetMapping("/get-report/{year}")
	public ResponseEntity<?> get(
			@PathVariable("year") Integer year){
		return new ResponseEntity<>(iKpiService.printReport(year), HttpStatus.OK);
	}
}
