package com.jay.tdd.wiremock;


import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.SocketUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = WiremockInitializer.class)
@Slf4j
public class HelloControllerWiremockTest {

    @Value("${api.server.port}")
    private int port;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        log.info("setup.. : " + port);
    }

    @AfterEach
    void tearDown() {
        log.info("td.. : " + wireMockServer.isRunning());
    }

    @Test
    public void testWelcome() {

        givenThat(get(urlMatching("/hello")).willReturn(aResponse().withBody("Hello Wiremock")));

        ResponseEntity<String> response = restTemplate.getForEntity("/hello", String.class);
        Assertions.assertEquals(200, response.getStatusCode());


    }

}


@Slf4j
class WiremockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final int port = SocketUtils.findAvailableTcpPort();

        TestPropertyValues.of("api.server.port:" + port).applyTo(applicationContext);
        WireMockServer wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        log.info("wireMockServer started... on port : " +port);

        applicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);

        // Stops wireMockServer
        applicationContext.addApplicationListener(event -> {
            if (event instanceof ContextClosedEvent) {
                wireMockServer.stop();
                log.info("wireMockServer stopped.");
            }
        });
    }
}