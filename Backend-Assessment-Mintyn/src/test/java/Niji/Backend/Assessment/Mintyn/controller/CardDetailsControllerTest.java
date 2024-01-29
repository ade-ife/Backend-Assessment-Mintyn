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



//
//    @Test
//    public void getCardStats_withDefaults_returnsExpectedResponse() {
//        // Mock cardDetailsService to return expected response
//        CardStatisticsResponse expectedResponse = new CardStatisticsResponse(
//                50L, 0, 10, Map.of("visa", 25, "mastercard", 25), true
//        );
//        when(cardService.getCardStatistics(0, 10)).thenReturn(expectedResponse);
//
//        // Call the endpoint
//        MockHttpServletResponse response = mockMvc.perform(get("/stats"))
//                .andExpect(status().isOk())
//                .andReturn().getResponse();
//
//        // Assert response content
//        CardStatisticsResponse actualResponse = objectMapper.readValue(response.getContentAsString(), CardStatisticsResponse.class);
//        assertEquals(expectedResponse, actualResponse);
//    }

}
