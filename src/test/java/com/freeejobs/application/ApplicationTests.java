package com.freeejobs.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.freeejobs.jobApplication.JobAppApplication;


@SpringBootTest(classes=JobAppApplication.class)
class ApplicationTests {

	@Test
	void contextLoads() {
		JobAppApplication.main(new String[] {});
	}

}
