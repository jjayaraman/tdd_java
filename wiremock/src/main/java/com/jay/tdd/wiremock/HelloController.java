package com.jay.tdd.wiremock;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jayakumar Jayaraman
 */
@RestController
public class HelloController {

    @GetMapping(path = "/hello")
    public ResponseEntity hello() {
        return ResponseEntity.ok("Hello World");
    }
}
