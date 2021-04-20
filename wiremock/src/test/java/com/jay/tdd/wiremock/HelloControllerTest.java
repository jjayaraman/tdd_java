package com.jay.tdd.wiremock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

/**
 * @author Jayakumar Jayaraman
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HelloControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHello() {
        String uri = "/hello";
        ResponseEntity response = restTemplate.getForEntity(uri, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getStatusCodeValue());
        System.out.println(response.getHeaders());

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals("Hello service", response.getBody());
    }
}