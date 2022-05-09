package com.freeejobs.jobApplication.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.freeejobs.jobApplication.model.JobApplicationAudit;
import com.freeejobs.jobApplication.response.Status;
import com.freeejobs.jobApplication.constant.JobApplicationStatusEnum;
import com.freeejobs.jobApplication.dto.JobApplicationDTO;
import com.freeejobs.jobApplication.model.JobApplication;

public class ApplicationFixture {
	
	public static JobApplication createJobApplication() {

		JobApplication jobApp = new JobApplication();
		jobApp.setId(Long.valueOf(1));
		jobApp.setJobId(Long.valueOf(1));
        jobApp.setApplicantId(Long.valueOf(1));
        jobApp.setStatus(JobApplicationStatusEnum.PENDING_ACCEPTANCE.getCode());
        jobApp.setDescription("Test Description");
        jobApp.setDateCreated(new Date());
        jobApp.setDateUpdated(new Date());
        

        return jobApp;
    }
	
	public static JobApplicationDTO createJobApplicationDTO() {

		JobApplicationDTO jobAppDTO = new JobApplicationDTO();
		jobAppDTO.setId(Long.valueOf(1));
		jobAppDTO.setJobId(Long.valueOf(1));
		jobAppDTO.setApplicantId(Long.valueOf(1));
		jobAppDTO.setStatus(JobApplicationStatusEnum.PENDING_ACCEPTANCE.getCode());
		jobAppDTO.setDescription("Test Description");
		jobAppDTO.setToken("Test Token");
        

        return jobAppDTO;
    }
	
	public static JobApplicationDTO createJobApplicationDTO_Accepted() {

		JobApplicationDTO jobAppDTO = new JobApplicationDTO();
		jobAppDTO.setId(Long.valueOf(1));
		jobAppDTO.setJobId(Long.valueOf(1));
		jobAppDTO.setApplicantId(Long.valueOf(1));
		jobAppDTO.setStatus(JobApplicationStatusEnum.ACCEPTED.getCode());
		jobAppDTO.setDescription("Test Description");
		jobAppDTO.setToken("Test Token");
        

        return jobAppDTO;
    }
	
	public static JobApplicationDTO createJobApplicationDTO_Rejected() {

		JobApplicationDTO jobAppDTO = new JobApplicationDTO();
		jobAppDTO.setId(Long.valueOf(1));
		jobAppDTO.setJobId(Long.valueOf(1));
		jobAppDTO.setApplicantId(Long.valueOf(1));
		jobAppDTO.setStatus(JobApplicationStatusEnum.REJECTED.getCode());
		jobAppDTO.setDescription("Test Description");
		jobAppDTO.setToken("Test Token");
        

        return jobAppDTO;
    }
	
	public static List<JobApplication> createJobApplicationList() {

        List<JobApplication> jobApps = new ArrayList<>();
        JobApplication jobApp = new JobApplication();
		jobApp.setId(Long.valueOf(1));
		jobApp.setJobId(Long.valueOf(1));
        jobApp.setApplicantId(Long.valueOf(1));
        jobApp.setDescription("Test Description");
        jobApp.setStatus(JobApplicationStatusEnum.PENDING_ACCEPTANCE.getCode());
        jobApp.setDateCreated(new Date());
        jobApp.setDateUpdated(new Date());
        jobApps.add(jobApp);

        

        return jobApps;
    }
	
	public static List<JobApplication> createJobApplicationListAcceptedStatus() {

        List<JobApplication> jobApps = new ArrayList<>();
        JobApplication jobApp = new JobApplication();
		jobApp.setId(Long.valueOf(1));
		jobApp.setJobId(Long.valueOf(1));
        jobApp.setApplicantId(Long.valueOf(1));
        jobApp.setDescription("Test Description");
        jobApp.setStatus(JobApplicationStatusEnum.ACCEPTED.getCode());
        jobApp.setDateCreated(new Date());
        jobApp.setDateUpdated(new Date());
        jobApps.add(jobApp);

        

        return jobApps;
    }
	
	public static JobApplicationAudit createJobApplicationAudit() {

		JobApplicationAudit jobApplicationAudit = new JobApplicationAudit();
		jobApplicationAudit.setAuditData("Audit");
		jobApplicationAudit.setCreatedBy("SYSTEM");
		jobApplicationAudit.setDateCreated(new Date());
		jobApplicationAudit.setId(1);
        

        return jobApplicationAudit;
    }
	
	
}