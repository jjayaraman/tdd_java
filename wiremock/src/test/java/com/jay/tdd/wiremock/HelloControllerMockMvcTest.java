package com.jay.tdd.wiremock;

import com.jay.tdd.wiremock.service.HelloService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Jayakumar Jayaraman
 */
@WebMvcTest
public class HelloControllerMockMvcTest {



    @MockBean
    HelloService helloService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHello() throws Exception {

        when(helloService.welcome()).thenReturn("Hello mock");

        String uri = "/hello";
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.is("Hello mock")))
        ;
    }
}