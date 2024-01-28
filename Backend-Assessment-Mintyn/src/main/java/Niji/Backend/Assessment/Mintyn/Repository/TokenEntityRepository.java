package Niji.Backend.Assessment.Mintyn.Repository;

import Niji.Backend.Assessment.Mintyn.Entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenEntityRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByToken (String token);

    List<TokenEntity> findTokenEntityByExpiredTimeIsLessThanEqual(LocalDateTime date);
}
