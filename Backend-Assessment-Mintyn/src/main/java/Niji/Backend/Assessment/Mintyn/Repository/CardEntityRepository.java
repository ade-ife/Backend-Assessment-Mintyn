package Niji.Backend.Assessment.Mintyn.Repository;

import Niji.Backend.Assessment.Mintyn.Entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardEntityRepository extends JpaRepository <CardEntity, Long>{

    Optional<CardEntity> findByBin(String bin);
}
