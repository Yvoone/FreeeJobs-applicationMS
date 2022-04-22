package com.freeejobs.application.controller;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.context.WebApplicationContext;

import com.freeejobs.jobApplication.WebConfig;
import com.freeejobs.jobApplication.constant.JobApplicationStatusEnum;
import com.freeejobs.jobApplication.controller.JobApplicationController;
import com.freeejobs.jobApplication.dto.JobApplicationDTO;
import com.freeejobs.jobApplication.response.APIResponse;
import com.freeejobs.jobApplication.response.Status;
import com.freeejobs.jobApplication.model.JobApplication;
import com.freeejobs.jobApplication.model.JobApplicationAudit;
import com.freeejobs.jobApplication.service.JobApplicationService;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {
	
	@Mock
	private JobApplicationService jobApplicationService;
	
	@InjectMocks
    private JobApplicationController jobApplicationController;
	
	private JobApplication jobApplication;
	private JobApplicationDTO jobApplicationDTO;
	private List<JobApplication> jobApplications;
    private JobApplicationAudit jobApplicationAudit;
	private int numberOfListingPerPage=10;
	
	@BeforeEach
    void setUp() {
		jobApplication = ApplicationFixture.createJobApplication();
		jobApplicationDTO = ApplicationFixture.createJobApplicationDTO();
		jobApplications = ApplicationFixture.createJobApplicationList();        
		jobApplicationAudit = ApplicationFixture.createJobApplicationAudit();
    }
	
	//getJobListingById
	
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicantsByJobId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        Date date = new Date();
        jobApplications.get(0).setDateCreated(date);
        jobApplications.get(0).setDateUpdated(date);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(true);
        when(jobApplicationService.listJobApplicantsByJobId(jobId)).thenReturn(jobApplications);

        APIResponse resJobApp = jobApplicationController.listJobApplicantsByJobId(response, jobId);
        assertEquals(jobApplications.get(0).getId(), ((List<JobApplication>) resJobApp.getData()).get(0).getId());
        assertEquals(jobApplications.get(0).getDateCreated(), ((List<JobApplication>) resJobApp.getData()).get(0).getDateCreated());
        assertEquals(jobApplications.get(0).getDateUpdated(), ((List<JobApplication>) resJobApp.getData()).get(0).getDateUpdated());
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicantsByJobId_notID() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(false);
        //when(jobApplicationService.listJobApplicantsByJobId(jobId)).thenReturn(jobApplications);

        APIResponse resJobApp = jobApplicationController.listJobApplicantsByJobId(response, jobId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicantsByJobId_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(true);
        when(jobApplicationService.listJobApplicantsByJobId(jobId)).thenReturn(null);

        APIResponse resJobApp = jobApplicationController.listJobApplicantsByJobId(response, jobId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicantsByJobId_exception() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(true);
        when(jobApplicationService.listJobApplicantsByJobId(jobId)).thenThrow(UnexpectedRollbackException.class);

        APIResponse resJobApp = jobApplicationController.listJobApplicantsByJobId(response, jobId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicationByApplicantIdAndStatus() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        String status = JobApplicationStatusEnum.PENDING_ACCEPTANCE.getCode();
        when(jobApplicationService.isStatusInvalid(status)).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
        when(jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, status)).thenReturn(jobApplications);

        APIResponse resJobApp = jobApplicationController.listJobApplicationByApplicantIdAndStatus(response, applicantId, status);
        assertEquals(jobApplications.get(0).getId(), ((List<JobApplication>) resJobApp.getData()).get(0).getId());
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicationByApplicantIdAndStatus_statusInvalid() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        String status = JobApplicationStatusEnum.PENDING_ACCEPTANCE.getCode();
        when(jobApplicationService.isStatusInvalid(status)).thenReturn(true);
//        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
//        when(jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, status)).thenReturn(jobApplications);

        APIResponse resJobApp = jobApplicationController.listJobApplicationByApplicantIdAndStatus(response, applicantId, status);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicationByApplicantIdAndStatus_notID() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        String status = JobApplicationStatusEnum.PENDING_ACCEPTANCE.getCode();
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(false);

        APIResponse resJobApp = jobApplicationController.listJobApplicationByApplicantIdAndStatus(response, applicantId, status);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicationByApplicantIdAndStatus_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        String status = JobApplicationStatusEnum.PENDING_ACCEPTANCE.getCode();
        when(jobApplicationService.isStatusInvalid(status)).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
        when(jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, status)).thenReturn(null);

        APIResponse resJobApp = jobApplicationController.listJobApplicationByApplicantIdAndStatus(response, applicantId, status);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicationByApplicantIdAndStatus_exception() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        String status = JobApplicationStatusEnum.PENDING_ACCEPTANCE.getCode();
        when(jobApplicationService.isStatusInvalid(status)).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
        when(jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, status)).thenThrow(UnexpectedRollbackException.class);

        APIResponse resJobApp = jobApplicationController.listJobApplicationByApplicantIdAndStatus(response, applicantId, status);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicationByApplicantId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
        when(jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, "")).thenReturn(jobApplications);

        APIResponse resJobApp = jobApplicationController.listJobApplicationByApplicantId(response, applicantId);
        assertEquals(jobApplications.get(0).getId(), ((List<JobApplication>) resJobApp.getData()).get(0).getId());
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicationByApplicantId_notID() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(false);
        //when(jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, "")).thenReturn(jobApplications);

        APIResponse resJobApp = jobApplicationController.listJobApplicationByApplicantId(response, applicantId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicationByApplicantId_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
        when(jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, "")).thenReturn(null);

        APIResponse resJobApp = jobApplicationController.listJobApplicationByApplicantId(response, applicantId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	@SuppressWarnings("unchecked")
	@Test
    void testListJobApplicationByApplicantId_exception() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
        when(jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, "")).thenThrow(UnexpectedRollbackException.class);

        APIResponse resJobApp = jobApplicationController.listJobApplicationByApplicantId(response, applicantId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListAcceptedApplicantsByJobId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(true);
        when(jobApplicationService.listAcceptedJobApplicantsByJobId(jobId)).thenReturn(jobApplications);

        APIResponse resJobApp = jobApplicationController.listAcceptedApplicantsByJobId(response, jobId);
        assertEquals(jobApplications.get(0).getId(), ((List<JobApplication>) resJobApp.getData()).get(0).getId());
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListAcceptedApplicantsByJobId_notID() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(false);

        APIResponse resJobApp = jobApplicationController.listAcceptedApplicantsByJobId(response, jobId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListAcceptedApplicantsByJobId_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(true);
        when(jobApplicationService.listAcceptedJobApplicantsByJobId(jobId)).thenReturn(null);

        APIResponse resJobApp = jobApplicationController.listAcceptedApplicantsByJobId(response, jobId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	@SuppressWarnings("unchecked")
	@Test
    void testListAcceptedApplicantsByJobId_exception() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(true);
        when(jobApplicationService.listAcceptedJobApplicantsByJobId(jobId)).thenThrow(UnexpectedRollbackException.class);

        APIResponse resJobApp = jobApplicationController.listAcceptedApplicantsByJobId(response, jobId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	
	
	@Test
    void testApplyJob() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        Status stat = new Status();
		stat.setStatusCode(Status.Type.OK.getCode());
		stat.setMessage("Successfully  apply Job.");
		stat.setStatusText(Status.Type.OK.getText());
		
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobApplication.getJobId(),jobApplication.getApplicantId())).thenReturn(jobApp);
        when(jobApplicationService.isBlank(jobApplication.getDescription())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(true);
        when(jobApplicationService.applyJob(jobApplicationDTO)).thenReturn(jobApplication);
        
        APIResponse resJobApp = jobApplicationController.applyJob(response, jobApplicationDTO);
        
        Status resStatus = resJobApp.getStatus();

        assertEquals(resStatus.getStatusCode(), stat.getStatusCode());
        assertEquals(resStatus.getStatusText(), stat.getStatusText());
        assertEquals(resStatus.getMessage(), stat.getMessage());
        
        assertEquals(jobApplication.getId(), ((JobApplication) resJobApp.getData()).getId());
    }
	
	@Test
    void testApplyJob_duplicateApplication() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = jobApplication;
        
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobApplication.getJobId(),jobApplication.getApplicantId())).thenReturn(jobApp);
        
        APIResponse resJobApp = jobApplicationController.applyJob(response, jobApplicationDTO);
        verify(jobApplicationService, Mockito.times(0)).applyJob(jobApplicationDTO);

        assertEquals(resJobApp.getData(), null);
    }

	@Test
    void testApplyJob_descriptionIsBlank() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobApplication.getJobId(),jobApplication.getApplicantId())).thenReturn(jobApp);
        when(jobApplicationService.isBlank(jobApplication.getDescription())).thenReturn(true);
        
        APIResponse resJobApp = jobApplicationController.applyJob(response, jobApplicationDTO);
        verify(jobApplicationService, Mockito.times(0)).applyJob(jobApplicationDTO);

        assertEquals(resJobApp.getData(), null);
    }


	@Test
    void testApplyJob_notApplicantId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobApplication.getJobId(),jobApplication.getApplicantId())).thenReturn(jobApp);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(false);
        
        APIResponse resJobApp = jobApplicationController.applyJob(response, jobApplicationDTO);
        verify(jobApplicationService, Mockito.times(0)).applyJob(jobApplicationDTO);

        assertEquals(resJobApp.getData(), null);
    }

	@Test
    void testApplyJob_notJobId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobApplication.getJobId(),jobApplication.getApplicantId())).thenReturn(jobApp);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(false);
        
        APIResponse resJobApp = jobApplicationController.applyJob(response, jobApplicationDTO);
        verify(jobApplicationService, Mockito.times(0)).applyJob(jobApplicationDTO);

        assertEquals(resJobApp.getData(), null);
    }
	
	@Test
    void testApplyJob_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobApplication.getJobId(),jobApplication.getApplicantId())).thenReturn(jobApp);
        when(jobApplicationService.isBlank(jobApplication.getDescription())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(true);
        when(jobApplicationService.applyJob(jobApplicationDTO)).thenReturn(null);
        APIResponse resJobApp = jobApplicationController.applyJob(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	
	@Test
    void testApplyJob_ThrowException() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobApplication.getJobId(),jobApplication.getApplicantId())).thenReturn(jobApp);
        when(jobApplicationService.isBlank(jobApplication.getDescription())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(true);
        when(jobApplicationService.applyJob(jobApplicationDTO)).thenThrow(UnexpectedRollbackException.class);
        APIResponse resJobApp = jobApplicationController.applyJob(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	
	@Test
    void testSetApplicationStatus() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(true);
        when(jobApplicationService.setAppStatus(jobApplicationDTO)).thenReturn(jobApplication);
        
        APIResponse resJobApp = jobApplicationController.setApplicationStatus(response, jobApplicationDTO);
        assertEquals(jobApplication.getId(), ((JobApplication) resJobApp.getData()).getId());
    }
	
	@Test
    void testSetApplicationStatus_invalidStatus() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(true);
        
        APIResponse resJobApp = jobApplicationController.setApplicationStatus(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	@Test
    void testSetApplicationStatus_isNotApplicantId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(false);
        
        APIResponse resJobApp = jobApplicationController.setApplicationStatus(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	@Test
    void testSetApplicationStatus_isNotJobId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(false);
        
        APIResponse resJobApp = jobApplicationController.setApplicationStatus(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	
	@Test
    void testSetApplicationStatus_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(true);
        when(jobApplicationService.setAppStatus(jobApplicationDTO)).thenReturn(null);
        
        APIResponse resJobApp = jobApplicationController.setApplicationStatus(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	
	@Test
    void testSetApplicationStatus_throwException() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(true);
        when(jobApplicationService.setAppStatus(jobApplicationDTO)).thenThrow(UnexpectedRollbackException.class);
        
        APIResponse resJobApp = jobApplicationController.setApplicationStatus(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	
	@Test
    void testCloseAppStatus() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplicationDTO.getJobId()))).thenReturn(true);
        when(jobApplicationService.closeAppStatus(jobApplicationDTO)).thenReturn(jobApplication);
        
        APIResponse resJobApp = jobApplicationController.closeAppStatus(response, jobApplicationDTO);
        assertEquals(jobApplicationDTO.getId(), ((JobApplication) resJobApp.getData()).getId());
    }
	
	@Test
    void testCloseAppStatus_invalidStatus() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(true);
        
        APIResponse resJobApp = jobApplicationController.closeAppStatus(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }

	@Test
    void testCloseAppStatus_isNotJobId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(false);
        
        APIResponse resJobApp = jobApplicationController.closeAppStatus(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	
	@Test
    void testCloseAppStatus_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(true);
        when(jobApplicationService.closeAppStatus(jobApplicationDTO)).thenReturn(null);
        
        APIResponse resJobApp = jobApplicationController.closeAppStatus(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	
	@Test
    void testCloseAppStatus_throwException() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        JobApplication jobApp = null;
        
        when(jobApplicationService.isStatusInvalid(jobApplicationDTO.getStatus())).thenReturn(false);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getApplicantId()))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(jobApplication.getJobId()))).thenReturn(true);
        when(jobApplicationService.closeAppStatus(jobApplicationDTO)).thenThrow(UnexpectedRollbackException.class);
        
        APIResponse resJobApp = jobApplicationController.closeAppStatus(response, jobApplicationDTO);
        assertEquals(resJobApp.getData(), null);
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListAcceptedJobApplicationByApplicantId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
        when(jobApplicationService.listAcceptedJobApplicationByApplicantId(applicantId)).thenReturn(jobApplications);

        APIResponse resJobApp = jobApplicationController.listAcceptedJobApplicationByApplicantId(response, applicantId);
        assertEquals(jobApplications.get(0).getId(), ((List<JobApplication>) resJobApp.getData()).get(0).getId());
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListAcceptedJobApplicationByApplicantId_notID() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(false);
        //when(jobApplicationService.listJobApplicationByApplicantIdAndStatus(applicantId, "")).thenReturn(jobApplications);

        APIResponse resJobApp = jobApplicationController.listAcceptedJobApplicationByApplicantId(response, applicantId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testListAcceptedJobApplicationByApplicantId_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
        when(jobApplicationService.listAcceptedJobApplicationByApplicantId(applicantId)).thenReturn(null);

        APIResponse resJobApp = jobApplicationController.listAcceptedJobApplicationByApplicantId(response, applicantId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	@SuppressWarnings("unchecked")
	@Test
    void testListAcceptedJobApplicationByApplicantId_exception() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long applicantId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(applicantId))).thenReturn(true);
        when(jobApplicationService.listAcceptedJobApplicationByApplicantId(applicantId)).thenThrow(UnexpectedRollbackException.class);

        APIResponse resJobApp = jobApplicationController.listAcceptedJobApplicationByApplicantId(response, applicantId);
        assertEquals(null, ((List<JobApplication>) resJobApp.getData()));
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testGetUserApplicationStatus() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        Long userId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(userId))).thenReturn(true);
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobId,userId)).thenReturn(jobApplication);

        APIResponse resJobApp = jobApplicationController.getUserApplicationStatus(response, jobId,userId);
        assertEquals(jobApplication.getStatus(), ((JobApplicationDTO) resJobApp.getData()).getStatus());
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testGetUserApplicationStatus_notJobID() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        Long userId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(false);

        APIResponse resJobApp = jobApplicationController.getUserApplicationStatus(response, jobId,userId);
        assertEquals(null, ((JobApplicationDTO) resJobApp.getData()).getStatus());
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testGetUserApplicationStatus_notUserID() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        Long userId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(userId))).thenReturn(false);

        APIResponse resJobApp = jobApplicationController.getUserApplicationStatus(response, jobId,userId);
        assertEquals(null, ((JobApplicationDTO) resJobApp.getData()).getStatus());
    }
	
	@SuppressWarnings("unchecked")
	@Test
    void testGetUserApplicationStatus_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        Long userId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(userId))).thenReturn(true);
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobId,userId)).thenReturn(null);

        APIResponse resJobApp = jobApplicationController.getUserApplicationStatus(response, jobId,userId);
        assertEquals("", ((JobApplicationDTO) resJobApp.getData()).getStatus());
    }
	@SuppressWarnings("unchecked")
	@Test
    void testGetUserApplicationStatus_exception() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long jobId = Long.valueOf(1);
        Long userId = Long.valueOf(1);
        when(jobApplicationService.isId(String.valueOf(jobId))).thenReturn(true);
        when(jobApplicationService.isId(String.valueOf(userId))).thenReturn(true);
        when(jobApplicationService.getJobApplicationByJobIdAndApplicantId(jobId,userId)).thenThrow(UnexpectedRollbackException.class);

        APIResponse resJobApp = jobApplicationController.getUserApplicationStatus(response, jobId,userId);
        assertEquals(null, ((JobApplicationDTO) resJobApp.getData()).getStatus());
    }
	
}