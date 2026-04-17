package org.example.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalculatorControllerTests {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void addEndpointReturnsSum() {
        webTestClient.get().uri("/api/add?a=2&b=3")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class).isEqualTo(5.0);
    }

    @Test
    void subtractEndpointReturnsDifference() {
        webTestClient.get().uri("/api/subtract?a=10&b=4")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class).isEqualTo(6.0);
    }

    @Test
    void multiplyEndpointReturnsProduct() {
        webTestClient.get().uri("/api/multiply?a=3&b=4")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class).isEqualTo(12.0);
    }

    @Test
    void divideEndpointReturnsQuotient() {
        webTestClient.get().uri("/api/divide?a=8&b=2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class).isEqualTo(4.0);
    }

    @Test
    void divideByZeroReturnsError() {
        webTestClient.get().uri("/api/divide?a=5&b=0")
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
