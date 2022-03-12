package com.freeejobs.jobApplication.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freeejobs.jobApplication.model.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
	public JobApplication findById(long id);
	public List<JobApplication> findAllJobApplicationByJobIdAndStatus(long jobId, String status);
	public JobApplication findByJobIdAndApplicantId(long jobId, long applicantId);
	public List<JobApplication> findAll();

	@Query("select t from JobApplication t where t.jobId = ?1")
	public List<JobApplication> findByJobId(long jobId);

	@Query("select t from JobApplication t where t.applicantId = ?1")
	public List<JobApplication> findByApplicantId(long applicantId);

	@Modifying
	@Transactional
	@Query("update JobApplication t set t.status = :status, t.dateUpdated = :currDate where t.jobId = :jobId")
	public void updateAllAppStatusbyJobId(@Param("jobId") long jobId, @Param("status") String status, @Param("currDate") Date currDate);

	public List<JobApplication> findAllJobApplicationByApplicantIdAndStatus(long applicantId, String status);

}
