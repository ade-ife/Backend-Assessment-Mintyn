package Niji.Backend.Assessment.Mintyn.Service;

import Niji.Backend.Assessment.Mintyn.Dtos.CardStatisticsResponse;
import Niji.Backend.Assessment.Mintyn.Dtos.VerifyCardDetailsDto;
import Niji.Backend.Assessment.Mintyn.Pojo.SchemeVerificationModel;
import reactor.core.publisher.Mono;



public interface CardDetailsService {
    CardStatisticsResponse getCardStatistics(int start, int limit);
    Mono<SchemeVerificationModel> validateCard(VerifyCardDetailsDto cardDetailsDto);


}
