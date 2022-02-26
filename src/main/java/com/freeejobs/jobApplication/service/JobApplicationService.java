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
		return jobApplicationRepository.findAllJobApplicationByJobIdAndStatus(jobId, "PA");
	}

	public List<JobApplication> listAcceptedJobApplicantsByJobId(long jobId) {
		return jobApplicationRepository.findAllJobApplicationByJobIdAndStatus(jobId, "A");
	}

	private JobApplication getJobApplicationByJobIdAndApplicantId (long jobId, long applicantId) {
		JobApplication jobApp = null;

		jobApp = jobApplicationRepository.findByJobIdAndApplicantId(jobId, applicantId);
		return jobApp;
	}

	public JobApplication applyJob(JobApplicationDTO jobAppDTO) {
		JobApplication jobApp = new JobApplication();
		jobApp.setApplicantId(jobAppDTO.getApplicantId());
		jobApp.setJobId(jobAppDTO.getJobId());
		jobApp.setDescription(jobAppDTO.getDescription());
		jobApp.setStatus("PA");

		return jobApplicationRepository.save(jobApp);
	}

	public JobApplication setAppStatus(JobApplicationDTO jobAppDTO) {
		if (jobAppDTO.getStatus().equals("A")) {
			updateAllApplicants(jobAppDTO.getJobId(), "R");
		}
		else if (!jobAppDTO.getStatus().equals("R")) {
			return null;
		}

		JobApplication jobApp = getJobApplicationByJobIdAndApplicantId(jobAppDTO.getJobId(), jobAppDTO.getApplicantId());
		jobApp.setStatus(jobAppDTO.getStatus());

		return jobApplicationRepository.save(jobApp);
	}

	public JobApplication closeAppStatus(JobApplicationDTO jobAppDTO) {
		JobApplication jobApp = new JobApplication();
		jobApp.setStatus(jobAppDTO.getStatus());
		if (jobAppDTO.getStatus().equals("C")) {
			updateAllApplicants(jobAppDTO.getJobId(), jobAppDTO.getStatus());
		}
		else {
			return null;
		}

		return jobApplicationRepository.save(jobApp);
	}

	private void updateAllApplicants(long jobId, String status) {
		jobApplicationRepository.updateAllAppStatusbyJobId(jobId, status);
	}

	public List<JobApplication> listJobApplicationByApplicantId(long applicantId) {
		return jobApplicationRepository.findByApplicantId(applicantId);
	}
	
	public List<JobApplication> listAcceptedJobApplicationByApplicantId(long applicantId) {
		return jobApplicationRepository.findAllJobApplicationByApplicantIdAndStatus(applicantId, "A");
	}

}
