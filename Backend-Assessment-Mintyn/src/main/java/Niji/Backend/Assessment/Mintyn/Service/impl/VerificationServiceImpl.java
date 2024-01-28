package Niji.Backend.Assessment.Mintyn.Service.impl;

import Niji.Backend.Assessment.Mintyn.Exception.CardNotFoundException;
import Niji.Backend.Assessment.Mintyn.Exception.CommonException;
import Niji.Backend.Assessment.Mintyn.Exception.RateLimitExceededException;
import Niji.Backend.Assessment.Mintyn.Pojo.CardResponse;
import Niji.Backend.Assessment.Mintyn.Pojo.SchemeVerificationModel;
import Niji.Backend.Assessment.Mintyn.Service.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VerificationServiceImpl implements VerificationService {

    private final WebClient webClient;

    public VerificationServiceImpl(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl("https://lookup.binlist.net/").build();
    }

    @Override
    public Mono<SchemeVerificationModel> verifyScheme(String bin) {
        return verifySchemeCall(bin)
                .map(cardResponse -> {
                    return convertToEntity(cardResponse);
                })
                .onErrorMap(ex -> new CommonException("Error", ex));
    }

    private SchemeVerificationModel convertToEntity(CardResponse cardResponse) {
        return SchemeVerificationModel.builder()
                .scheme(cardResponse.getScheme())
                .bank(cardResponse.getBank().getName())
                .type(cardResponse.getType())
                .build();
    }


    public Mono<CardResponse> verifySchemeCall(String bin) {
        return this.webClient.get()
                .uri(bin)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    if (response.statusCode().equals(HttpStatus.TOO_MANY_REQUESTS)) {
                        return Mono.error(new RateLimitExceededException("Too many requests made. Kindly try again later."));
                    }

                    return Mono.error(new CardNotFoundException("The card scheme could not be identified for the given BIN: " + bin));
                })
                .bodyToMono(CardResponse.class)
                .handle((cardResponse, sink) -> {
                    if (cardResponse == null || isInvalidCardResponse(cardResponse)) {
                        sink.error(new CardNotFoundException("The card scheme could not be identified for the given BIN:0: " + bin));
                    } else {
                        sink.next(cardResponse);
                    }
                });
    }


    private boolean isInvalidCardResponse(CardResponse cardResponse) {
        return cardResponse.getScheme() == null || cardResponse.getBank() == null || cardResponse.getType() == null;
    }

}
