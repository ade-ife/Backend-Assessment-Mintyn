package Niji.Backend.Assessment.Mintyn.controller;

import Niji.Backend.Assessment.Mintyn.Controller.CardDetailsController;
import Niji.Backend.Assessment.Mintyn.Dtos.CardStatisticsResponse;
import Niji.Backend.Assessment.Mintyn.Pojo.CardDetailsResponse;
import Niji.Backend.Assessment.Mintyn.Pojo.SchemeVerificationModel;
import Niji.Backend.Assessment.Mintyn.Service.CardDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardDetailsControllerTest {


    @Mock
    private CardDetailsService cardService;

    @InjectMocks
    private CardDetailsController cardController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(cardController).build();
    }



    @Test
    void getCardStats_ShouldReturnCardStatistics() {
        // Given
        int start = 0;
        int limit = 10;
        Map<String, Integer> expectedData = Map.of(
                "42639826", 1,
                "45717360", 7
        );
        CardStatisticsResponse expectedResponse = new CardStatisticsResponse(2, start, limit, expectedData, true);

        when(cardService.getCardStatistics(start, limit)).thenReturn(expectedResponse);

        // When and Then
        webTestClient.get().uri("/v1/api/card-scheme/stats?start={start}&limit={limit}", start, limit)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.start").isEqualTo(start)
                .jsonPath("$.limit").isEqualTo(limit)
                .jsonPath("$.size").isEqualTo(2)
                .jsonPath("$.data.42639826").isEqualTo(1)
                .jsonPath("$.data.45717360").isEqualTo(7);
    }


    @Test
    void verifyCardWithValidBin() {
        SchemeVerificationModel model = new SchemeVerificationModel("debit", "Jyske Bank A/S", "visa");
        when(cardService.validateCard(any())).thenReturn(Mono.just(model));

        webTestClient.get().uri("/v1/api/card-scheme/verify/45717360")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.payload.scheme").isEqualTo("visa")
                .jsonPath("$.payload.type").isEqualTo("debit")
                .jsonPath("$.payload.bank").isEqualTo("Jyske Bank A/S");
    }

    @Test
    void verifyCardWithInvalidBin() {
        when(cardService.validateCard(any())).thenReturn(Mono.empty());

        webTestClient.get().uri("/v1/api/card-scheme/verify/87651275")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(false)
                .jsonPath("$.payload").isEqualTo(null);
    }





}
