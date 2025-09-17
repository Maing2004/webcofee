package com.example.ZVnMobile.service.impl;

import java.util.List;

import com.example.ZVnMobile.dto.KpiDto;
import com.example.ZVnMobile.dto.ReportYearDto;
import com.example.ZVnMobile.payload.DataResponse;

public interface IKpiService {
	DataResponse insertKpi(KpiDto kpiDto);
	DataResponse updateKpi(KpiDto kpiDto);
	DataResponse getBySearch(Integer year, Integer month, String type);
	DataResponse findAllKpi();
	DataResponse printReport(Integer year);
}
