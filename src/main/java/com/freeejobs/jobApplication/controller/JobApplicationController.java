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
import com.freeejobs.jobApplication.dto.JobApplicationDTO;

@RestController
@RequestMapping(value="/jobApplication")
@CrossOrigin
public class JobApplicationController {

	@Autowired
	private JobApplicationService jobApplicationService;

	@RequestMapping(value="/listApplicantsByJobId", method= RequestMethod.GET)
	public List<JobApplication> listJobApplicantsByJobId(HttpServletResponse response,
			@RequestParam long jobId) throws URISyntaxException {

		List<JobApplication> jobApplication = null;

		try {
			System.out.println(jobId);
			jobApplication = jobApplicationService.listJobApplicantsByJobId(jobId);
				if(jobApplication == null) {
					System.out.println("null");
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return null;
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
				}



		} catch (Exception e) {
			System.out.println(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return jobApplication;
	}

	@RequestMapping(value="/applyJob", method= RequestMethod.POST)
	public JobApplication applyJob(HttpServletResponse response,
			@RequestBody JobApplicationDTO jobAppDTO) throws URISyntaxException {

		JobApplication applyjob = null;

		try {
			System.out.println(jobAppDTO.getJobId());
			applyjob = jobApplicationService.applyJob(jobAppDTO);
				if(applyjob == null) {
					System.out.println("null");
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return null;
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
				}



		} catch (Exception e) {
			System.out.println(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return applyjob;
	}

}
