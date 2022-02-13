package com.freeejobs.jobApplication.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freeejobs.jobApplication.model.JobApplication;
import com.freeejobs.jobApplication.repository.JobApplicationRepository;
import com.freeejobs.jobApplication.dto.JobApplicationDTO;

@Service
public class JobApplicationService {

	private static final Logger LOGGER = LogManager.getLogger(JobApplicationService.class);

	@Autowired
	private JobApplicationRepository jobApplicationRepository;

	public JobApplication getJobApplicationById(long jobId) {
		return jobApplicationRepository.findById(jobId);
	}

	public List<JobApplication> listJobApplicantsByJobId(long jobId) {
		return jobApplicationRepository.findByJobId(jobId);
	}

	public JobApplication applyJob(JobApplicationDTO jobAppDTO) {
		JobApplication jobApp = new JobApplication();
		jobApp.setApplicantId(jobAppDTO.getApplicantId());
		jobApp.setJobId(jobAppDTO.getJobId());
		jobApp.setDescription(jobAppDTO.getDescription());
		jobApp.setStatus("Pending");

		return jobApplicationRepository.save(jobApp);
	}

	public List<JobApplication> listJobApplicationByApplicantId(long applicantId) {
		return jobApplicationRepository.findByApplicantId(applicantId);
	}

}
