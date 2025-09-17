package com.example.ZVnMobile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ZVnMobile.entities.KpiEntity;


@Repository
public interface KpiRepository extends JpaRepository<KpiEntity, Long>{
	KpiEntity findOneById(Long id);
	KpiEntity findOneByYearAndMonthAndType(Integer year, Integer month, String type);
	List<KpiEntity> findAll();
}
