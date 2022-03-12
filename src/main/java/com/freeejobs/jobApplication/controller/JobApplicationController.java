package com.freeejobs.jobApplication.controller;

import java.io.Console;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freeejobs.jobApplication.model.JobApplication;
import com.freeejobs.jobApplication.service.JobApplicationService;
import com.freeejobs.jobApplication.response.APIResponse;
import com.freeejobs.jobApplication.response.Status;
import com.freeejobs.jobApplication.dto.JobApplicationDTO;

@RestController
@RequestMapping(value="/jobApplication")
@CrossOrigin
public class JobApplicationController {

	@Autowired
	private JobApplicationService jobApplicationService;

	@RequestMapping(value="/listApplicantsByJobId", method= RequestMethod.GET)
	public APIResponse listJobApplicantsByJobId(HttpServletResponse response,
			@RequestParam long jobId) throws URISyntaxException {

		List<JobApplication> jobApplication = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			System.out.println(jobId);
			jobApplication = jobApplicationService.listJobApplicantsByJobId(jobId);
				if(jobApplication == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Applicants By JobId.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully list Applicants By JobId.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Applicants By JobId, Exception");
		}
		resp.setData(jobApplication);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value="/listJobApplicationByApplicantIdAndStatus", method= RequestMethod.GET)
	public APIResponse listJobApplicationByApplicantIdAndStatus(HttpServletResponse response,
			@RequestParam long applicantId, @RequestParam String status) throws URISyntaxException {

		List<JobApplication> jobApplication = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			System.out.println(applicantId);
			jobApplication = jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, status);
				if(jobApplication == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list JobApplication By ApplicantId And Status.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully list JobApplication By ApplicantId And Status.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list JobApplication By ApplicantId And Status, Exception");
		}
		resp.setData(jobApplication);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value="/listAcceptedApplicantsByJobId", method= RequestMethod.GET)
	public APIResponse listAcceptedApplicantsByJobId(HttpServletResponse response,
			@RequestParam long jobId) throws URISyntaxException {

		List<JobApplication> jobApplication = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			System.out.println(jobId);
			jobApplication = jobApplicationService.listAcceptedJobApplicantsByJobId(jobId);
				if(jobApplication == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Applicants By JobId.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully list Accepted Applicants By JobId.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Applicants By JobId, Exception");
		}
		resp.setData(jobApplication);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value="/applyJob", method= RequestMethod.POST)
	public APIResponse applyJob(HttpServletResponse response,
			@RequestBody JobApplicationDTO jobAppDTO) throws URISyntaxException {

		JobApplication applyjob = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		//TODO need to check if applicant apply alr a not similar to commented codes below
//		List<Rating> ratings = ratingService.getRatingsByReviewerIdJobId(rating.getReviewerId(), rating.getJobId());
//		if(ratings.size()>0) {
//			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to create rating. Rating already created.");
//			resp.setStatus(responseStatus);
//			return resp;
//		}
		try {
			System.out.println(jobAppDTO.getJobId());
			applyjob = jobApplicationService.applyJob(jobAppDTO);
				if(applyjob == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to apply Job.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully  apply Job.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to apply Job, Exception");
		}
		resp.setData(applyjob);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value="/setAppStatus", method= RequestMethod.POST)
	public APIResponse setApplicationStatus(HttpServletResponse response,
			@RequestBody JobApplicationDTO jobAppDTO) throws URISyntaxException {

		JobApplication setAppStatus = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			System.out.println(jobAppDTO.getJobId());
			setAppStatus = jobApplicationService.setAppStatus(jobAppDTO);
				if(setAppStatus == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to set Application Status.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully set Application Status.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to set Application Status, Exception");
		}
		resp.setData(setAppStatus);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value="/closeAppStatus", method= RequestMethod.POST)
	public APIResponse closeAppStatus(HttpServletResponse response,
			@RequestBody JobApplicationDTO jobAppDTO) throws URISyntaxException {

		JobApplication closeAppStatus = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			System.out.println(jobAppDTO.getJobId());
			closeAppStatus = jobApplicationService.closeAppStatus(jobAppDTO);
				if(closeAppStatus == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to close Application Status.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully close Application Status.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to close Application Status, Exception");
		}
		resp.setData(closeAppStatus);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value="/listAcceptedJobApplicationByApplicantId", method= RequestMethod.GET)
	public APIResponse listAcceptedJobApplicationByApplicantId(HttpServletResponse response,
			@RequestParam long applicantId) throws URISyntaxException {

		List<JobApplication> jobApplication = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			System.out.println(applicantId);
			jobApplication = jobApplicationService.listAcceptedJobApplicationByApplicantId(applicantId);
				if(jobApplication == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Job Application By ApplicantId.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully list Accepted Job Application By ApplicantId.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Job Application By ApplicantId, Exception");
		}
		resp.setData(jobApplication);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value="/getUserApplicationStatus", method= RequestMethod.GET)
	public APIResponse getUserApplicationStatus(HttpServletResponse response,
			@RequestParam long jobId, @RequestParam long userId) throws URISyntaxException {

		JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			System.out.println(jobId);
			JobApplication jobApplication = jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobId,userId);
				if(jobApplication == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					jobApplicationDTO.setStatus("");
					responseStatus = new Status(Status.Type.OK, "Successfully get User Application Status.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					jobApplicationDTO.setStatus(jobApplication.getStatus());
					responseStatus = new Status(Status.Type.OK, "Successfully get User Application Status.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get User Application Status, Exception");
		}
		resp.setData(jobApplicationDTO);
		resp.setStatus(responseStatus);
		return resp;
	}
}
