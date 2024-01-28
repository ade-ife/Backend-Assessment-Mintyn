package Niji.Backend.Assessment.Mintyn.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name= "card")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scheme;

    private String bank;

    private String type;

    private int count;

    private String bin;

//    public CardEntity(String scheme, String bank, String type, int count, String bin) {
//
//        this.scheme = scheme;
//        this.bank = bank;
//        this.type = type;
//        this.count = count;
//        this.bin = bin;
//
//    }

}
