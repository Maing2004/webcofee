package com.example.ZVnMobile.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ZVnMobile.dto.KpiDto;
import com.example.ZVnMobile.dto.ReportYearDto;
import com.example.ZVnMobile.dto.SaleOrderDto;
import com.example.ZVnMobile.entities.KpiEntity;
import com.example.ZVnMobile.payload.DataResponse;
import com.example.ZVnMobile.repository.KpiRepository;
import com.example.ZVnMobile.repository.OrderRepository;
import com.example.ZVnMobile.service.impl.IKpiService;
import com.example.ZVnMobile.utils.PoiReportUtils;

@Service
public class KpiService implements IKpiService {

	@Autowired
	private KpiRepository kpiRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PoiReportUtils poiReportUtils;

	@Override
	public DataResponse insertKpi(KpiDto kpiDto) {
		DataResponse dataResponse = new DataResponse();
		LocalDate currentDate = LocalDate.now();
		try {
			KpiEntity entity = new KpiEntity();
			entity.setYear(currentDate.getYear());
			entity.setMonth(currentDate.getMonthValue());
			entity.setType(kpiDto.getType());
			entity.setKpi(kpiDto.getKpi());
			entity.setNote(kpiDto.getNote());
			entity.setDeadline(kpiDto.getDeadline());
			entity = kpiRepository.save(entity);

			dataResponse.setMessage("OK");
			dataResponse.setSuccess(true);
			dataResponse.setData(entity.getKpi());
		} catch (Exception e) {
			dataResponse.setMessage("Error");
			dataResponse.setErrorCode(e.getMessage());
			dataResponse.setSuccess(false);
		}
		return dataResponse;
	}

	@Override
	public DataResponse updateKpi(KpiDto kpiDto) {
		DataResponse dataResponse = new DataResponse();
		try {
			KpiEntity entity = kpiRepository.findOneById(kpiDto.getId());
			entity.setType(kpiDto.getType());
			entity.setKpi(kpiDto.getKpi());
			entity.setNote(kpiDto.getNote());
			entity.setDeadline(kpiDto.getDeadline());
			entity = kpiRepository.save(entity);

			dataResponse.setMessage("OK");
			dataResponse.setSuccess(true);
			dataResponse.setData(entity.getKpi());
		} catch (Exception e) {
			dataResponse.setMessage("Error");
			dataResponse.setErrorCode(e.getMessage());
			dataResponse.setSuccess(false);
		}
		return dataResponse;
	}

	@Override
	public DataResponse getBySearch(Integer year, Integer month, String type) {
		DataResponse dataResponse = new DataResponse();
		try {
			KpiEntity entity = kpiRepository.findOneByYearAndMonthAndType(year, month, type);
			KpiDto dto = new KpiDto();
			dto.setYear(entity.getYear());
			dto.setMonth(entity.getMonth());
			dto.setType(entity.getType());
			dto.setKpi(entity.getKpi());
			dto.setNote(entity.getNote());
			dto.setDeadline(entity.getDeadline());

			dataResponse.setMessage("OK");
			dataResponse.setSuccess(true);
			dataResponse.setData(dto);
		} catch (Exception e) {
			dataResponse.setMessage("Error");
			dataResponse.setErrorCode(e.getMessage());
			dataResponse.setSuccess(false);
		}
		return dataResponse;
	}

	@Override
	public DataResponse findAllKpi() {
		DataResponse dataResponse = new DataResponse();
		try {
			List<KpiEntity> listEntities = kpiRepository.findAll();
			List<KpiDto> listDtos = new ArrayList<>();
			for (KpiEntity entity : listEntities) {
				KpiDto dto = new KpiDto();
				dto.setYear(entity.getYear());
				dto.setMonth(entity.getMonth());
				dto.setType(entity.getType());
				dto.setKpi(entity.getKpi());
				dto.setNote(entity.getNote());
				dto.setDeadline(entity.getDeadline());
				listDtos.add(dto);
			}
			dataResponse.setMessage("OK");
			dataResponse.setSuccess(true);
			dataResponse.setData(listDtos);
		} catch (Exception e) {
			dataResponse.setMessage("Error");
			dataResponse.setErrorCode(e.getMessage());
			dataResponse.setSuccess(false);
		}
		return dataResponse;
	}

	@Override
	public DataResponse printReport(Integer year) {
		DataResponse dataResponse = new DataResponse();
		try {
			List<Object[]> results = orderRepository.getMonthlyOrderStatistics(year);
//			List<SaleOrderDto> saleOrderDtos = results.stream()
//		            .map(result -> new SaleOrderDto(
//		                ((Number) result[0]).intValue(),
//		                ((Number) result[1]).intValue(),
//		                ((Number) result[2]).longValue(),
//		                (BigDecimal) result[3]
//		            ))
//		            .collect(Collectors.toList());
			List<SaleOrderDto> saleOrderDtos = new ArrayList<>(12);
			IntStream.rangeClosed(1, 12).forEach(month -> {
				saleOrderDtos.add(new SaleOrderDto(year, month, 0, BigDecimal.ZERO));
			});
			results.forEach(result -> {
				int month = ((Number) result[1]).intValue();
				SaleOrderDto dto = saleOrderDtos.get(month - 1);
				dto.setTotalOrders(((Number) result[2]).longValue());
				dto.setRevenue((BigDecimal) result[3]);
			});
			Boolean isSuccess = poiReportUtils.createReportBill(saleOrderDtos);
			if(isSuccess == true) {
				dataResponse.setMessage("OK");
				dataResponse.setData(saleOrderDtos);
				dataResponse.setSuccess(true);
			}
			else {
				dataResponse.setMessage("NO");
				dataResponse.setData(saleOrderDtos);
				dataResponse.setSuccess(false);
			}
		} catch (Exception e) {
			dataResponse.setMessage("Error");
			dataResponse.setErrorCode(e.getMessage());
			dataResponse.setSuccess(false);
		}
		return dataResponse;
	}

}
