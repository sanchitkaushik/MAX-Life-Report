package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class ProposalFormRuleDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String kickoutMsg;
	private String resultFlag;

	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getKickoutMsg() {
		return kickoutMsg;
	}

	public void setKickoutMsg(String kickoutMsg) {
		this.kickoutMsg = kickoutMsg;
	}

}
