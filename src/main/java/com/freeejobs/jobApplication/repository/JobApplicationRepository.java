package com.freeejobs.jobApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.freeejobs.jobApplication.model.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
	public JobApplication findById(long id);
	public List<JobApplication> findAll();

	@Query("select t from JobApplication t where t.jobId = ?1")
	public List<JobApplication> findByJobId(long jobId);

	@Query("select t from JobApplication t where t.applicantId = ?1")
	public List<JobApplication> findByApplicantId(long applicantId);

}
