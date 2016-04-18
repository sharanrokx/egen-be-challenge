package io.egen;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.egen.controller.AlertRule;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TrackerApplication.class)
@WebAppConfiguration
public class TrackerApplicationTests {

	@Test
	public void contextLoads() {
		AlertRule alertRule =new AlertRule(null, 166);
		Assert.assertTrue(alertRule.checkInput());
	}

}
