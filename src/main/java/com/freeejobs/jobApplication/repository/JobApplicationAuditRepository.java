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
import com.freeejobs.jobApplication.model.JobApplicationAudit;

@Repository
public interface JobApplicationAuditRepository extends JpaRepository<JobApplicationAudit, Long> {
	
}
