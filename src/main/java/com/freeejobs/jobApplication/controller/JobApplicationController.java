package com.freeejobs.jobApplication.controller;

import java.io.Console;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.freeejobs.jobApplication.constant.JobApplicationStatusEnum;
import com.freeejobs.jobApplication.dto.JobApplicationDTO;

@RestController
@RequestMapping(value="/jobApplication")
@CrossOrigin("https://freeejobs-web.herokuapp.com")
public class JobApplicationController {

	private static Logger LOGGER = LogManager.getLogger(JobApplicationController.class);
	
	@Autowired
	private JobApplicationService jobApplicationService;

	@RequestMapping(value="/listApplicantsByJobId", method= RequestMethod.GET)
	public APIResponse listJobApplicantsByJobId(HttpServletResponse response,
			@RequestParam long jobId) throws URISyntaxException {

		List<JobApplication> jobApplication = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			if(!jobApplicationService.isId(String.valueOf(jobId))){
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Applicants By JobId. Invalid job Id.");
				LOGGER.error(responseStatus.toString());
			}else {
				System.out.println(jobId);
				jobApplication = jobApplicationService.listJobApplicantsByJobId(jobId);
					if(jobApplication == null) {
						//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						//return null;
						responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Applicants By JobId.");
						LOGGER.error(responseStatus.toString());
					} else {
						//response.setStatus(HttpServletResponse.SC_OK);
						responseStatus = new Status(Status.Type.OK, "Successfully list Applicants By JobId.");
					}
			}		
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Applicants By JobId, Exception");
			LOGGER.error(e.getMessage(), e);
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
		List<String> errors = new ArrayList<String>();
		
		try {
			if(jobApplicationService.isStatusInvalid(status)) {
				errors.add("Invalid status value");
			}
			if(!jobApplicationService.isId(String.valueOf(applicantId))){
				errors.add("Invalid author id value");
			}
			if(errors.isEmpty()) {
				System.out.println(applicantId);
				jobApplication = jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, status);
					if(jobApplication == null) {
						//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						//return null;
						responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list JobApplication By ApplicantId And Status.");
						LOGGER.error(responseStatus.toString());
						
					} else {
						//response.setStatus(HttpServletResponse.SC_OK);
						responseStatus = new Status(Status.Type.OK, "Successfully list JobApplication By ApplicantId And Status.");
					}
			}else {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list JobApplication By ApplicantId And Status. Invalid status or id.");
				String listOfErrors = errors.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
				LOGGER.error(listOfErrors);
			}
		}catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list JobApplication By ApplicantId And Status, Exception");
			LOGGER.error(e.getMessage(), e);
		}
		resp.setData(jobApplication);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value="/listJobApplicationByApplicantId", method= RequestMethod.GET)
	public APIResponse listJobApplicationByApplicantId(HttpServletResponse response,
			@RequestParam long applicantId) throws URISyntaxException {

		List<JobApplication> jobApplication = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		List<String> errors = new ArrayList<String>();
		
		try {
			if(!jobApplicationService.isId(String.valueOf(applicantId))){
				errors.add("Invalid author id value");
			}
			if(errors.isEmpty()) {
				System.out.println(applicantId);
				jobApplication = jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, "");
					if(jobApplication == null) {
						//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						//return null;
						responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list JobApplication By ApplicantId.");
						LOGGER.error(responseStatus.toString());
						
					} else {
						//response.setStatus(HttpServletResponse.SC_OK);
						responseStatus = new Status(Status.Type.OK, "Successfully list JobApplication By ApplicantId.");
					}
			}else {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list JobApplication By ApplicantId. Invalid id.");
				String listOfErrors = errors.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
				LOGGER.error(responseStatus.toString()+" "+listOfErrors);
			}
		}catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list JobApplication By ApplicantId, Exception");
			LOGGER.error(e.getMessage(), e);
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
			if(!jobApplicationService.isId(String.valueOf(jobId))){
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Applicants By JobId. Invalid job Id.");
				LOGGER.error(responseStatus.toString());
			}else {
				System.out.println(jobId);
				jobApplication = jobApplicationService.listAcceptedJobApplicantsByJobId(jobId);
					if(jobApplication == null) {
						//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						//return null;
						responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Applicants By JobId.");
						LOGGER.error(responseStatus.toString());
						
					} else {
						//response.setStatus(HttpServletResponse.SC_OK);
						responseStatus = new Status(Status.Type.OK, "Successfully list Accepted Applicants By JobId.");
					}
			}
			
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Applicants By JobId, Exception");
			LOGGER.error(e.getMessage(), e);
		}
		resp.setData(jobApplication);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value={"/applyJob","/android/applyJob"}, method= RequestMethod.POST)
	public APIResponse applyJob(HttpServletResponse response,
			@RequestBody JobApplicationDTO jobAppDTO) throws URISyntaxException {

		JobApplication applyjob = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");

		List<String> errors = new ArrayList<String>();
		
		try {
			//if applied before alr straight away return
			JobApplication jobApplication = jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobAppDTO.getJobId(),jobAppDTO.getApplicantId());
			if(jobApplication!=null) {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to apply Job. User Already Applied For the Job.");
				LOGGER.error(responseStatus.toString());
				resp.setStatus(responseStatus);
				return resp;
			}
			if(jobApplicationService.isBlank(jobAppDTO.getDescription())) {
				errors.add("Invalid description value");
			}
			if(!jobApplicationService.isId(String.valueOf(jobAppDTO.getApplicantId()))) {
				errors.add("Invalid applicant id value");
			}
			if(!jobApplicationService.isId(String.valueOf(jobAppDTO.getJobId()))) {
				errors.add("Invalid job id value");
			}
			if(errors.isEmpty()) {
				System.out.println(jobAppDTO.getJobId());
				applyjob = jobApplicationService.applyJob(jobAppDTO);
					if(applyjob == null) {
						//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						//return null;
						responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to apply Job.");
						LOGGER.error(responseStatus.toString());
						
					} else {
						//response.setStatus(HttpServletResponse.SC_OK);
						responseStatus = new Status(Status.Type.OK, "Successfully  apply Job.");
					}
			}else {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to apply Job. Invalid Application Object.");
				String listOfErrors = errors.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
				LOGGER.error(responseStatus.toString()+" "+listOfErrors);
			}
			
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to apply Job, Exception");
			LOGGER.error(e.getMessage(), e);
		}
		resp.setData(applyjob);
		resp.setStatus(responseStatus);
		return resp;
	}

	@RequestMapping(value={"/setAppStatus","/android/setAppStatus"}, method= RequestMethod.POST)
	public APIResponse setApplicationStatus(HttpServletResponse response,
			@RequestBody JobApplicationDTO jobAppDTO) throws URISyntaxException {

		JobApplication setAppStatus = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		List<String> errors = new ArrayList<String>();
		
		try {
			if(jobApplicationService.isStatusInvalid(jobAppDTO.getStatus())) {
				errors.add("Invalid status value");
			}
			if(!jobApplicationService.isId(String.valueOf(jobAppDTO.getApplicantId()))) {
				errors.add("Invalid applicant id value");
			}
			if(!jobApplicationService.isId(String.valueOf(jobAppDTO.getJobId()))) {
				errors.add("Invalid job id value");
			}
			if(errors.isEmpty()) {
				System.out.println(jobAppDTO.getJobId());
				setAppStatus = jobApplicationService.setAppStatus(jobAppDTO);
					if(setAppStatus == null) {
						//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						//return null;
						responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to set Application Status.");
						LOGGER.error(responseStatus.toString());
						
					} else {
						//response.setStatus(HttpServletResponse.SC_OK);
						responseStatus = new Status(Status.Type.OK, "Successfully set Application Status.");
					}
			}else {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to set Application Status. Invalid Application Object.");
				String listOfErrors = errors.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
				LOGGER.error(responseStatus.toString()+" "+listOfErrors);
			}
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to set Application Status, Exception");
			LOGGER.error(e.getMessage(), e);
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
		List<String> errors = new ArrayList<String>();

		try {
			if (jobApplicationService.isStatusInvalid(jobAppDTO.getStatus())) {
				errors.add("Invalid status value");
			}
			if (!jobApplicationService.isId(String.valueOf(jobAppDTO.getJobId()))) {
				errors.add("Invalid job id value");
			}
			if (errors.isEmpty()) {
				System.out.println(jobAppDTO.getJobId());
				closeAppStatus = jobApplicationService.closeAppStatus(jobAppDTO);
				if (closeAppStatus == null) {
					// response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					// return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR,
							"Failed to close Application Status.");
					LOGGER.error(responseStatus.toString());
				} else {
					// response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully close Application Status.");
				}
			} else {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR,
						"Failed to close Application Status. Invalid Application Object.");
				String listOfErrors = errors.stream().map(Object::toString).collect(Collectors.joining(", "));
				LOGGER.error(responseStatus.toString() + " " + listOfErrors);
			}
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to close Application Status, Exception");
			LOGGER.error(e.getMessage(), e);
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
			if(!jobApplicationService.isId(String.valueOf(applicantId))){
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Job Application By ApplicantId. Invalid applicant Id.");
				LOGGER.error(responseStatus.toString());
			}else {
				System.out.println(applicantId);
				jobApplication = jobApplicationService.listAcceptedJobApplicationByApplicantId(applicantId);
					if(jobApplication == null) {
						//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						//return null;
						responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Job Application By ApplicantId.");
						LOGGER.error(responseStatus.toString());
					} else {
						//response.setStatus(HttpServletResponse.SC_OK);
						responseStatus = new Status(Status.Type.OK, "Successfully list Accepted Job Application By ApplicantId.");
					}
			}
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to list Accepted Job Application By ApplicantId, Exception");
			LOGGER.error(e.getMessage(), e);
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
		List<String> errors = new ArrayList<String>();

		try {
			if (!jobApplicationService.isId(String.valueOf(userId))) {
				errors.add("Invalid user id value");
			}
			if (!jobApplicationService.isId(String.valueOf(jobId))) {
				errors.add("Invalid job id value");
			}
			if (errors.isEmpty()) {
				System.out.println(jobId);
				JobApplication jobApplication = jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobId,userId);
					if(jobApplication == null) {
						//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						//return null;
						jobApplicationDTO.setStatus("");
						responseStatus = new Status(Status.Type.OK, "Failed to get User Application Status.");
						LOGGER.error(responseStatus.toString());
					} else {
						//response.setStatus(HttpServletResponse.SC_OK);
						jobApplicationDTO.setStatus(jobApplication.getStatus());
						responseStatus = new Status(Status.Type.OK, "Successfully get User Application Status.");
					}
			}else {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR,
						"Failed to get User Application Status. Invalid Application Object.");
				String listOfErrors = errors.stream().map(Object::toString).collect(Collectors.joining(", "));
				LOGGER.error(responseStatus.toString() + " " + listOfErrors);
			}
		}
		catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get User Application Status, Exception");
			LOGGER.error(e.getMessage(), e);
		}
		resp.setData(jobApplicationDTO);
		resp.setStatus(responseStatus);
		return resp;
	}
}
