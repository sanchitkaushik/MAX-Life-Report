package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class ChannelDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String branchCode;
	private String branchName;
	private String channel;
	private String originalChannel;

	public String getOriginalChannel() {
		return originalChannel;
	}

	public void setOriginalChannel(String originalChannel) {
		this.originalChannel = originalChannel;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
