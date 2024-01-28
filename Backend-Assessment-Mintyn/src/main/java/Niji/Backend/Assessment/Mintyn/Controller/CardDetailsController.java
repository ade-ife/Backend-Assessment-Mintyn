package Niji.Backend.Assessment.Mintyn.Controller;

import Niji.Backend.Assessment.Mintyn.Dtos.CardStatisticsResponse;
import Niji.Backend.Assessment.Mintyn.Dtos.VerifyCardDetailsDto;
import Niji.Backend.Assessment.Mintyn.Pojo.ApiResponse;
import Niji.Backend.Assessment.Mintyn.Pojo.SchemeVerificationModel;
import Niji.Backend.Assessment.Mintyn.Service.CardDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequestMapping(value = "v1/api/card-scheme", produces = APPLICATION_JSON_VALUE)
@RestController
public class CardDetailsController {

    private final CardDetailsService cardDetailsService;


    public CardDetailsController(CardDetailsService cardDetailsService) {
        this.cardDetailsService = cardDetailsService;
    }

    @GetMapping("/verify/{bin}")
    public Mono<ApiResponse<SchemeVerificationModel>> verifyCardScheme(@PathVariable String bin) {
        VerifyCardDetailsDto cardDetailsDto = VerifyCardDetailsDto.builder()
                .bin(bin).build();

        return cardDetailsService.validateCard(cardDetailsDto)
                .map(model -> new ApiResponse<>(true, model))
                .defaultIfEmpty(new ApiResponse<>(false, null));
    }

    @GetMapping("/stats")
    public CardStatisticsResponse getCardStats(
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "10") int limit) {
        return cardDetailsService.getCardStatistics(start, limit);
    }
}