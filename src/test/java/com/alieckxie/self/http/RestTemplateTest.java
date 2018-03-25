package com.alieckxie.self.http;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

	@Test
	public void testSimpleRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		String forObject = restTemplate.getForObject("https://www.baidu.com/s?wd=%E6%96%97%E9%B1%BCtv&rsv_spt=1&rsv_iqid=0x9b901d0300083d15&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=2&rsv_sug1=2&rsv_sug7=100&rsv_t=c58augRgRdmdXSgbFg1MFuS6wGH1h4D9cCFalwllX4Yx0NIawNct%2BjaQIHbgxdS3u4m5&sug=%25E6%2596%2597%25E9%25B1%25BCtv&rsv_n=1", String.class);
		System.out.println(forObject);
	}

}
