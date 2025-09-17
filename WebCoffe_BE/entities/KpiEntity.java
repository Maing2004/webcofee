package com.example.ZVnMobile.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "kpi")
public class KpiEntity extends BaseEntity{
	
	@Column(name = "year", columnDefinition = "INT")
	private Integer year;
	
	@Column(name = "month", columnDefinition = "INT")
	private Integer month;
	
	@Column(name = "type", columnDefinition = "VARCHAR(255)")
	private String type;
	
	@Column(name = "kpi", columnDefinition = "DECIMAL")
	private Double kpi;
	
	@Column(name = "note", columnDefinition = "TEXT")
	private String note;
	
	@Column(name = "deadline", columnDefinition = "TIMESTAMP")
	private Date deadline;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getKpi() {
		return kpi;
	}

	public void setKpi(Double kpi) {
		this.kpi = kpi;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

}
