package com.mysting.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ComputeService {

    @Autowired
    RestTemplate restTemplate;

    private static String url = "http://COMPUTE-SERVICE/add?a=10&b=20";

    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String addService() {
//        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).build().expand("").encode();
//        URI uri = uriComponents.toUri();
//        String responseEntity = restTemplate.getForObject(uri, String.class);
//        return restTemplate.getForObject(url, String.class);
        return restTemplate.getForEntity(url, String.class).getBody();
    }

    public String addServiceFallback() {
        return "error";
    }

}
