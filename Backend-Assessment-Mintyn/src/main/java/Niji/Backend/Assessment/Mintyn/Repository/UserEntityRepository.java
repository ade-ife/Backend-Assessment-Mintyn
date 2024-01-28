package Niji.Backend.Assessment.Mintyn.Repository;

import Niji.Backend.Assessment.Mintyn.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByUsername(String username);

    boolean existsUserEntityByUsername(String username);
}
