package com.proxym;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "Auth0-Management-API", url = "http://localhost:9090")
public interface Auth0ManagementFeignClient {
    @RequestMapping(value = "/verify")
    String verifySignatureFeign(@RequestParam("token") String token);


}
