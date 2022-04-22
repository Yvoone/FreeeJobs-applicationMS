package com.freeejobs.jobApplication.dto;

public class JobApplicationDTO {

    private long id;
	private long jobId;
	private long applicantId;
	private String status;
    private String description;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getJobId() {
		return jobId;
	}
	
	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public long getApplicantId() {
		return applicantId;
	}
	
	public void setApplicantId(long applicantId) {
		this.applicantId = applicantId;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
