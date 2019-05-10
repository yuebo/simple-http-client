package com.eappcat.http.client;

import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@SimpleClient(baseUrl = "https://www.baidu.com")
@Primary
public interface TestClient {
    @RequestMapping(method = RequestMethod.GET,path = "/s",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String test(@RequestParam("wd") String test);
}
