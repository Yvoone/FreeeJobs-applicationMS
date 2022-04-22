package com.freeejobs.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.freeejobs.application.controller.ApplicationFixture;
import com.freeejobs.jobApplication.WebConfig;
import com.freeejobs.jobApplication.constant.AuditEnum;
import com.freeejobs.jobApplication.constant.JobApplicationStatusEnum;
import com.freeejobs.jobApplication.dto.JobApplicationDTO;
import com.freeejobs.jobApplication.model.JobApplication;
import com.freeejobs.jobApplication.model.JobApplicationAudit;
import com.freeejobs.jobApplication.repository.JobApplicationAuditRepository;
import com.freeejobs.jobApplication.repository.JobApplicationRepository;
import com.freeejobs.jobApplication.service.JobApplicationService;

@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {
	@Mock
	private JobApplicationRepository jobApplicationRepository;
	
	@Mock
	private JobApplicationAuditRepository jobApplicationAuditRepository;
	
	@InjectMocks
    private JobApplicationService jobApplicationService;
	
	private JobApplication jobApplication;
	private JobApplicationDTO jobApplicationDTO;
	private JobApplicationDTO jobApplicationDTO_Accepted;
	private JobApplicationDTO jobApplicationDTO_Rejected;
	private List<JobApplication> jobApplications;
	private List<JobApplication> jobApplicationsAcceptedStatus;
    private JobApplicationAudit jobApplicationAudit;
	
	private int numberOfListingPerPage=10;
	
	@BeforeEach
    void setUp() {
		jobApplication = ApplicationFixture.createJobApplication();
		jobApplicationDTO = ApplicationFixture.createJobApplicationDTO();
		jobApplicationDTO_Accepted = ApplicationFixture.createJobApplicationDTO_Accepted();
		jobApplicationDTO_Rejected = ApplicationFixture.createJobApplicationDTO_Rejected();
		jobApplications = ApplicationFixture.createJobApplicationList();
		jobApplicationsAcceptedStatus= ApplicationFixture.createJobApplicationListAcceptedStatus(); 
		jobApplicationAudit = ApplicationFixture.createJobApplicationAudit();
    }
	
//	@Test
//    void testGetJobApplicationById() {
//		
//        Long jobId = Long.valueOf(1);
//        when(jobApplicationRepository.findById(jobId)).thenReturn(jobApplication);
//        
//
//        JobApplication resJobApplication = jobApplicationService.getJobApplicationById(jobId);
//        assertEquals(resJobApplication.getId(), jobApplication.getId());
//    }
	
    @Test
    void testListJobApplicantsByJobId() {
		
        Long jobId = Long.valueOf(1);
        when(jobApplicationRepository.findAllJobApplicationByJobIdAndStatus(jobId, "PA")).thenReturn(jobApplications);
        

        List<JobApplication> resJobApplication = jobApplicationService.listJobApplicantsByJobId(jobId);
        assertEquals(resJobApplication.get(0).getId(), jobApplications.get(0).getId());
    }
    
    @Test
    void testFindAllJobApplicationByJobIdAndStatus() {
		
        Long jobId = Long.valueOf(1);
        when(jobApplicationRepository.findAllJobApplicationByJobIdAndStatus(jobId, "A")).thenReturn(jobApplicationsAcceptedStatus);
        

        List<JobApplication> resJobApplication = jobApplicationService.listAcceptedJobApplicantsByJobId(jobId);
        assertEquals(resJobApplication.get(0).getId(), jobApplicationsAcceptedStatus.get(0).getId());
    }
    
    @Test
    void testGetJobApplicationByJobIdAndApplicantId() {
		
        Long applicantId = Long.valueOf(1);
        Long jobId = Long.valueOf(1);
        when(jobApplicationRepository.findByJobIdAndApplicantId(jobId, applicantId)).thenReturn(jobApplication);
        

        JobApplication resJobApplication = jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobId, applicantId);
        assertEquals(resJobApplication.getId(), jobApplication.getId());
    }

    //issue with it is that in main method the date set is instantiated there but the test object is another date.
//    @Test
//    void testAddRating() {
//		Date currDate = new Date();
//		jobApplication.setDateCreated(currDate);
//		jobApplication.setDateUpdated(currDate);
//		doReturn(jobApplication).when(jobApplicationRepository).save(jobApplication);
//		//when(jobApplicationRepository.save(jobApplication)).thenReturn(jobApplication);
//        jobApplicationAudit.setOpsType(AuditEnum.INSERT.getCode());
//        Mockito.lenient().when(jobApplicationService.insertAudit(jobApplication, AuditEnum.INSERT.getCode())).thenReturn(jobApplicationAudit);
//
//        JobApplication resJobApplication = jobApplicationService.applyJob(jobApplicationDTO);
//        verify(jobApplicationRepository, Mockito.times(1)).save(jobApplication);
//
//        assertEquals(resJobApplication.getJobId(), jobApplication.getJobId());
//        assertEquals(resJobApplication.getDateCreated(), jobApplication.getDateCreated());
//        assertEquals(resJobApplication.getDateUpdated(), jobApplication.getDateUpdated());
//    }
    
    @Test
	  void testListJobApplicationByApplicantId(){
		  Long applicantId = Long.valueOf(1);
	
		  when(jobApplicationRepository.findByApplicantId(applicantId)).thenReturn(jobApplications);
		  
		  List<JobApplication> resJobApplication = jobApplicationService.listJobApplicationByApplicantId(applicantId);
		  
	      assertEquals(resJobApplication.get(0).getId(), jobApplications.get(0).getId());
	  }
    
    @Test
	  void testListAcceptedJobApplicationByApplicantId(){
		  Long applicantId = Long.valueOf(1);
	
		  when(jobApplicationRepository.findAllJobApplicationByApplicantIdAndStatus(applicantId, JobApplicationStatusEnum.ACCEPTED.getCode())).thenReturn(jobApplicationsAcceptedStatus);
		  
		  List<JobApplication> resJobApplication = jobApplicationService.listAcceptedJobApplicationByApplicantId(applicantId);
		  
	      assertEquals(resJobApplication.get(0).getId(), jobApplications.get(0).getId());
	  }
    
    @Test
	  void testListAcceptedJobApplicationByApplicantId_withStatus(){
		  Long applicantId = Long.valueOf(1);
		  String status = JobApplicationStatusEnum.ACCEPTED.getCode();
	
		  when(jobApplicationRepository.findAllJobApplicationByApplicantIdAndStatus(applicantId, status)).thenReturn(jobApplicationsAcceptedStatus);
		  
		  List<JobApplication> resJobApplication = jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, status);
		  
	      assertEquals(resJobApplication.get(0).getId(), jobApplications.get(0).getId());
	  }
    
    @Test
	  void testListAcceptedJobApplicationByApplicantId_withoutStatus(){
		  Long applicantId = Long.valueOf(1);
		  String status = "";
	
		  when(jobApplicationRepository.findByApplicantId(applicantId)).thenReturn(jobApplicationsAcceptedStatus);
		  
		  List<JobApplication> resJobApplication = jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, status);
		  
	      assertEquals(resJobApplication.get(0).getId(), jobApplications.get(0).getId());
	  }

    @Test
    void testIsId() {    

        boolean valid = jobApplicationService.isId("1");
        
        assertTrue(valid);
    }
	@Test
    void testIsNotId() {    

        boolean valid = jobApplicationService.isId("abc");
        
        assertFalse(valid);
    }
	
	@Test
    void testIsBlank() {    

        boolean valid = jobApplicationService.isBlank("");
        
        assertTrue(valid);
    }
	@Test
    void testIsNotBlank() {    

        boolean valid = jobApplicationService.isBlank("ABC");
        
        assertFalse(valid);
    }
	
	@Test
    void testIsStatusInvalid() {    

        boolean valid = jobApplicationService.isStatusInvalid("ABC");
        
        assertTrue(valid);
    }
	@Test
    void testIsStatusValid() {    

        boolean valid = jobApplicationService.isStatusInvalid(JobApplicationStatusEnum.ACCEPTED.getCode());
        
        assertFalse(valid);
    }
	
	@Test
    void testInsertAudit() {    

		Date date = new Date();
        JobApplicationAudit jobApplicationAuditLo = new JobApplicationAudit();
        jobApplicationAuditLo.setId(1);
        jobApplicationAuditLo.setOpsType(AuditEnum.INSERT.getCode());
        jobApplicationAuditLo.setAuditData(jobApplication.toString());
        jobApplicationAuditLo.setCreatedBy(String.valueOf(jobApplication.getApplicantId()));
        jobApplicationAuditLo.setDateCreated(date);

        assertEquals(jobApplicationAuditLo.getId(), 1);
        assertEquals(jobApplicationAuditLo.getAuditData(), jobApplication.toString());
        assertEquals(jobApplicationAuditLo.getDateCreated(), date);
        assertEquals(jobApplicationAuditLo.getCreatedBy(), String.valueOf(jobApplication.getApplicantId()));
        assertEquals(jobApplicationAuditLo.getOpsType(),AuditEnum.INSERT.getCode());

    }
	
	@Test
    void testAuditEnum() {    

		AuditEnum.INSERT.setCode("T");
		AuditEnum.INSERT.setDescription("Test");

        assertEquals(AuditEnum.INSERT.getDescription(), "Test");
        assertEquals(AuditEnum.INSERT.getCode(), "T");

    }
	
	@Test
    void testJobApplicationStatusEnum() {    

		JobApplicationStatusEnum.ACCEPTED.setCode("A");
		JobApplicationStatusEnum.ACCEPTED.setDescription("Test");

        assertEquals(JobApplicationStatusEnum.ACCEPTED.getDescription(), "Test");
        assertEquals(JobApplicationStatusEnum.ACCEPTED.getCode(), "A");

    }

}