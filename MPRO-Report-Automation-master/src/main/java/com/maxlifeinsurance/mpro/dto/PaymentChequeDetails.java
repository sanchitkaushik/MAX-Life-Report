package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Qualtech
 *
 */
public class PaymentChequeDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private double chequeAmount;
	private Date chequeDate;
	private long chequeMicr;
	private long chequeNumber;

	public double getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(double chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public long getChequeMicr() {
		return chequeMicr;
	}

	public void setChequeMicr(long chequeMicr) {
		this.chequeMicr = chequeMicr;
	}

	public long getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(long chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

}
