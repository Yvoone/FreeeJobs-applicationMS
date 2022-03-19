package com.freeejobs.jobApplication.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freeejobs.jobApplication.model.JobApplication;
import com.freeejobs.jobApplication.repository.JobApplicationRepository;
import com.freeejobs.jobApplication.constant.JobApplicationStatusEnum;
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

	public JobApplication getJobApplicationByJobIdAndApplicantId (long jobId, long applicantId) {
		JobApplication jobApp = null;

		jobApp = jobApplicationRepository.findByJobIdAndApplicantId(jobId, applicantId);
		return jobApp;
	}

	public JobApplication applyJob(JobApplicationDTO jobAppDTO) {
		JobApplication jobApp = new JobApplication();
		Date currDate = new Date();
		jobApp.setApplicantId(jobAppDTO.getApplicantId());
		jobApp.setJobId(jobAppDTO.getJobId());
		jobApp.setDescription(jobAppDTO.getDescription());
		jobApp.setStatus(JobApplicationStatusEnum.PENDING_ACCEPTANCE.getCode());
		jobApp.setDateCreated(currDate);
		jobApp.setDateUpdated(currDate);

		return jobApplicationRepository.save(jobApp);
	}

	public JobApplication setAppStatus(JobApplicationDTO jobAppDTO) {
		if (jobAppDTO.getStatus().equals(JobApplicationStatusEnum.ACCEPTED.getCode())) {
			updateAllApplicants(jobAppDTO.getJobId(), JobApplicationStatusEnum.REJECTED.getCode());
		}
		else if (!jobAppDTO.getStatus().equals(JobApplicationStatusEnum.REJECTED.getCode())) {
			return null;
		}

		JobApplication jobApp = getJobApplicationByJobIdAndApplicantId(jobAppDTO.getJobId(), jobAppDTO.getApplicantId());
		jobApp.setStatus(jobAppDTO.getStatus());
		jobApp.setDateUpdated(new Date());

		return jobApplicationRepository.save(jobApp);
	}

	public JobApplication closeAppStatus(JobApplicationDTO jobAppDTO) {
		JobApplication jobApp = new JobApplication();
		jobApp.setStatus(jobAppDTO.getStatus());
		if (jobAppDTO.getStatus().equals(JobApplicationStatusEnum.CLOSED.getCode())) {
			updateAllApplicants(jobAppDTO.getJobId(), jobAppDTO.getStatus());
		}
		else {
			return null;
		}
		jobApp.setDateUpdated(new Date());
		return jobApplicationRepository.save(jobApp);
	}

	private void updateAllApplicants(long jobId, String status) {
		jobApplicationRepository.updateAllAppStatusbyJobId(jobId, status, new Date());
	}

	public List<JobApplication> listJobApplicationByApplicantId(long applicantId) {
		return jobApplicationRepository.findByApplicantId(applicantId);
	}

	public List<JobApplication> listAcceptedJobApplicationByApplicantId(long applicantId) {
		return jobApplicationRepository.findAllJobApplicationByApplicantIdAndStatus(applicantId, JobApplicationStatusEnum.ACCEPTED.getCode());
	}

	public List<JobApplication> listJobApplicationByApplicantIdAndStatus(long applicantId, String status) {
		System.out.println("status:"+status);
		if (status.isEmpty()) {
			return jobApplicationRepository.findByApplicantId(applicantId);
		}else {
			return jobApplicationRepository.findAllJobApplicationByApplicantIdAndStatus(applicantId, status);
		}

	}
	public boolean isId(String id) {
		return String.valueOf(id).matches("[0-9]+");
	}

}
