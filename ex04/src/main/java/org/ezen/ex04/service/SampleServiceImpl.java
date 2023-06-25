package org.ezen.ex04.service;

import org.springframework.stereotype.Service;

@Service
//@Log4j를 이용해 log.info()를 사용하던 것을 AOP를 이용하여 처리
public class SampleServiceImpl implements SampleService {

	@Override
	public Integer doAdd(String str1, String str2) throws Exception {
		
		return Integer.parseInt(str1) + Integer.parseInt(str2);
	}

}
