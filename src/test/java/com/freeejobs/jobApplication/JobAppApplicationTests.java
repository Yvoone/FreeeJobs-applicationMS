package com.freeejobs.jobApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.freeejobs.jobApplication.JobAppApplication;


@SpringBootTest(classes=JobAppApplication.class)
class JobAppApplicationTests {

	@Test
	void contextLoads() {
		JobAppApplication.main(new String[] {});
	}

}
