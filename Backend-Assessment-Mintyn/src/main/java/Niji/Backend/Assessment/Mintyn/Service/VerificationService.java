package Niji.Backend.Assessment.Mintyn.Service;

import Niji.Backend.Assessment.Mintyn.Pojo.SchemeVerificationModel;
import reactor.core.publisher.Mono;

public interface VerificationService {

    Mono<SchemeVerificationModel> verifyScheme(String bin);
}
