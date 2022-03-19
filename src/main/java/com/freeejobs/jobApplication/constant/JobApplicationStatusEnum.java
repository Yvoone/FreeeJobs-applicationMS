package com.freeejobs.jobApplication.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum JobApplicationStatusEnum {

	PENDING_ACCEPTANCE("PA","Pending Acceptance"), ACCEPTED("A", "Accepted"), CLOSED("C", "Closed"), 
	REJECTED("R","Rejected");
	
	public static class Constants{
		private Constants() {
		}
		
		public static final List<String> JOB_LISTING_STATUS_LIST =Collections.unmodifiableList(Arrays.asList(
				PENDING_ACCEPTANCE.getCode(), ACCEPTED.getCode(), CLOSED.getCode(), REJECTED.getCode()));
	}
	
	private String code;
	private String description;
	
	JobApplicationStatusEnum(final String code, String description){
		this.code=code;
		this.description=description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
