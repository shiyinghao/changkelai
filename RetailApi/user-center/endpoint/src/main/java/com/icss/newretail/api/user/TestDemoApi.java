package com.icss.newretail.api.user;

import com.icss.newretail.service.user.ITestDemoService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jc
 * @date 2020/3/16 19:05
 */
@RestSchema(schemaId = "userTest")
@RequestMapping(path = "/userTest")
public class TestDemoApi {

	@Autowired
	private ITestDemoService testDemoService;

	@GetMapping("test")
	public String test() {
		return testDemoService.test();
	}
}
