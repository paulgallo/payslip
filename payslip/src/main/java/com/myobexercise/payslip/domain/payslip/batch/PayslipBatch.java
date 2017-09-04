package com.myobexercise.payslip.domain.payslip.batch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myobexercise.payslip.domain.payslip.IncomeItem;
import com.myobexercise.payslip.domain.payslip.Payslip;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "PAYSLIP_BATCH")
public class PayslipBatch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "BATCHNAME", nullable = false)
	private String batchName;

	@JsonIgnore
	@Column(name = "PROCESSED_DATE_TIME", nullable = false)
	private LocalDateTime processedDateTime;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderColumn
	private List<Payslip> payslips;

	@Transient
	private String link;

	@Transient
	private List<IncomeItem> incomeItems;

	public PayslipBatch() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getProcessedDateTime() {
		return processedDateTime;
	}

	public void setProcessedDateTime(LocalDateTime processedDateTime) {
		this.processedDateTime = processedDateTime;
	}

	public List<Payslip> getPayslips() {
		return payslips;
	}

	public void setPayslips(List<Payslip> payslips) {
		this.payslips = payslips;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<IncomeItem> getIncomeItems() {
		return incomeItems;
	}

	public void setIncomeItems(List<IncomeItem> incomeItems) {
		this.incomeItems = incomeItems;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append(id).append(batchName).append(processedDateTime)
				.append(payslips).toString();
	}

}
