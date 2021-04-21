package com.jay.tdd.wiremock;

import com.jay.tdd.wiremock.service.HelloService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jayakumar Jayaraman
 */
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping(path = "/hello")
    public ResponseEntity hello() {
        String welcome = helloService.welcome();
        return ResponseEntity.ok(welcome);
    }
}
