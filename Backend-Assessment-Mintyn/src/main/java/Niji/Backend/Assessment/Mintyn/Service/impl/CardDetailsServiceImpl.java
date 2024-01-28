package Niji.Backend.Assessment.Mintyn.Service.impl;

import Niji.Backend.Assessment.Mintyn.Config.CacheManager;
import Niji.Backend.Assessment.Mintyn.Dtos.CardStatisticsResponse;
import Niji.Backend.Assessment.Mintyn.Dtos.VerifyCardDetailsDto;
import Niji.Backend.Assessment.Mintyn.Entities.CardEntity;
import Niji.Backend.Assessment.Mintyn.Pojo.Response.EnumResponseCodes;
import Niji.Backend.Assessment.Mintyn.Pojo.SchemeVerificationModel;
import Niji.Backend.Assessment.Mintyn.Repository.CardEntityRepository;
import Niji.Backend.Assessment.Mintyn.Service.CardDetailsService;
import Niji.Backend.Assessment.Mintyn.Service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CardDetailsServiceImpl implements CardDetailsService {

    private final CardEntityRepository cardEntityRepository;
    private final CacheManager cacheManager;
    private final VerificationService verificationService;


    @Override
    public CardStatisticsResponse getCardStatistics(int start, int limit) {
        CardStatisticsResponse response = new CardStatisticsResponse();

        Pageable pageable = PageRequest.of(start, limit);
        Page<CardEntity> page = cardEntityRepository.findAll(pageable);
        Map<String, Integer> data = page.getContent().stream()
                .collect(Collectors.toMap(CardEntity::getBin, CardEntity::getCount));

        response.setSuccess(true);
        response.setStart(start);
        response.setLimit(limit);
        response.setSize(page.getTotalElements());
        response.setData(data);
        response.setResponseCode("00");
        response.setResponseDescription(EnumResponseCodes.SUCCESS.toString());
        response.setResponseDescription("CARD STATISTICS FETCHED SUCCESSFULLY");

        return response;
    }

    @Override
    public Mono<SchemeVerificationModel> validateCard(VerifyCardDetailsDto cardDetailsDto) {
        return cacheManager.get(cardDetailsDto.getBin(), SchemeVerificationModel.class)
                .switchIfEmpty(Mono.defer(() -> fetchCardDetails(cardDetailsDto)))
                .doOnSuccess(model -> increaseCardCount(cardDetailsDto.getBin()));
    }


    private void increaseCardCount(String bin) {
        cardEntityRepository.findByBin(bin)
                .ifPresent(card -> {
                    card.setCount(card.getCount() + 1);
                    cardEntityRepository.save(card);
                });
    }

    private Mono<SchemeVerificationModel> fetchCardDetails(VerifyCardDetailsDto verifyCardDetailsDto) {
        return Mono.fromCallable(() -> cardEntityRepository.findByBin(verifyCardDetailsDto.getBin()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalCard -> handleCard(optionalCard, verifyCardDetailsDto));
    }

    private Mono<SchemeVerificationModel> handleCard(Optional<CardEntity> optionalCard, VerifyCardDetailsDto verifyCardDetailsDto) {
        if (optionalCard.isPresent()) {
            SchemeVerificationModel model = maptoModel(optionalCard.get());
            cacheManager.put(verifyCardDetailsDto.getBin(), model);
            return Mono.just(model);
        } else {
            return getDetailsFromProcessor(verifyCardDetailsDto);
        }
    }
    private Mono<SchemeVerificationModel> getDetailsFromProcessor(VerifyCardDetailsDto verifyCardDetailsDto) {

        return verificationService.verifyScheme(verifyCardDetailsDto.getBin())
                .flatMap(verifySchemeModel -> {
                    save(verifySchemeModel, verifyCardDetailsDto.getBin());
                    return Mono.fromRunnable(() -> cacheManager.put(verifyCardDetailsDto.getBin(), verifySchemeModel))
                            .thenReturn(verifySchemeModel);
                });
    }

    private SchemeVerificationModel maptoModel(CardEntity card) {
        return SchemeVerificationModel.builder()
                .scheme(card.getScheme())
                .bank(card.getBank())
                .type(card.getType())
                .build();
    }
    private void save(SchemeVerificationModel model, String bin) {
        CardEntity cardEntity = CardEntity.builder()
                .bin(bin)
                .count(0)
                .type(model.getType())
                .bank(model.getBank())
                .scheme(model.getScheme())
                .build();

        cardEntityRepository.save(cardEntity);
    }


}
