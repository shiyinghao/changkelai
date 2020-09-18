package com.icss.newretail.user.service.impl;

import com.icss.newretail.service.user.ITestDemoService;
import org.springframework.stereotype.Service;

/**
 * @author jc
 * @date 2020/3/16 19:10
 */
@Service
public class TestDemoServiceImpl implements ITestDemoService {
	@Override
	public String test() {
		return "test";
	}
}
