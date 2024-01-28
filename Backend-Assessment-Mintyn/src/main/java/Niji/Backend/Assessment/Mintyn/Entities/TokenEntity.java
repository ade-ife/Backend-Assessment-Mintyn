package Niji.Backend.Assessment.Mintyn.Entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name= "token")
public class TokenEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String token;
    @Column(name = "expired_time", nullable = false)
    protected LocalDateTime expiredTime;
    public TokenEntity(String token, LocalDateTime expiredTime) {
        this.token = token;
        this.expiredTime = expiredTime;
    }

}
