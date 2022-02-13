package com.freeejobs.jobApplication.dto;

public class JobApplicationDTO {

    private long id;
	private long jobId;
	private long applicantId;
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

	public long getApplicantId() {
		return applicantId;
	}

	public String getDescription() {
		return description;
	}
}
