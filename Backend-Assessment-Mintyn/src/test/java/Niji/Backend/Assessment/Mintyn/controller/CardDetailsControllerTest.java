package Niji.Backend.Assessment.Mintyn.controller;

import Niji.Backend.Assessment.Mintyn.Controller.CardDetailsController;
import Niji.Backend.Assessment.Mintyn.Pojo.SchemeVerificationModel;
import Niji.Backend.Assessment.Mintyn.Service.CardDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    void verifyCardScheme_WithValidBin_ShouldReturnCardInfo() {
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
    void verifyCardScheme_WithInvalidBin_ShouldReturnErrorMessage() {
        when(cardService.validateCard(any())).thenReturn(Mono.empty());

        webTestClient.get().uri("/v1/api/card-scheme/verify/87651275")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(false)
                .jsonPath("$.payload").isEqualTo(null);
    }


//    @Test
//    void getStats_ShouldReturnCardStatistics() {
//        int start = 0;
//        int limit = 10;
//        Map<String, Integer> expectedPayload = Map.of(
//                "45397912", 6,
//                "49218184", 1,
//                "42639826", 4,
//                "45717360", 1
//        );
//        CardStatsResponse mockResponse = new CardStatsResponse(true, start, limit, 4, expectedPayload);
//
//        when(cardService.getCardStats(start, limit)).thenReturn(mockResponse);
//
//        webTestClient.get().uri("/v1/api/card-scheme/stats", start, limit)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.success").isEqualTo(true)
//                .jsonPath("$.start").isEqualTo(start)
//                .jsonPath("$.limit").isEqualTo(limit)
//                .jsonPath("$.size").isEqualTo(4)
//                .jsonPath("$.payload.45397912").isEqualTo(6)
//                .jsonPath("$.payload.49218184").isEqualTo(1)
//                .jsonPath("$.payload.42639826").isEqualTo(4)
//                .jsonPath("$.payload.45717360").isEqualTo(1);
//    }
}
