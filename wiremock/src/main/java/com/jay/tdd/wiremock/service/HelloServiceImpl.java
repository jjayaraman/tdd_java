package com.jay.tdd.wiremock.service;

import org.springframework.stereotype.Service;

/**
 * @author Jayakumar Jayaraman
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String welcome() {
        return "Hello service";
    }
}
