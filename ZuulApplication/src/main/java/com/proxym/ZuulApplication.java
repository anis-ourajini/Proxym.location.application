package com.proxym;

/**
 * @author Anis OURAJINI
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ZuulApplication {
    public static void main(String[] args) throws MalformedURLException {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}