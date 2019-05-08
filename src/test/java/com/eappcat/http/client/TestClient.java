package com.eappcat.http.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@SimpleClient(baseUrl = "https://www.baidu.com")
public interface TestClient {
    @RequestMapping(method = RequestMethod.GET,path = "/s",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String test(@RequestParam("wd") String test);
}
