package Niji.Backend.Assessment.Mintyn.Pojo.VerificationPojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude
@Data
public class CountryInfo {

    private String name;
    private String currency;
    private String numeric;
    private String alpha2;
    private String emoji;
    private int latitude;
    private int longitude;
}
