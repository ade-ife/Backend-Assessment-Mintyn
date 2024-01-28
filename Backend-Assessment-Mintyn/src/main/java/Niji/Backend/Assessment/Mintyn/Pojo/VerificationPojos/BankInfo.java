package Niji.Backend.Assessment.Mintyn.Pojo.VerificationPojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude
@Data
public class BankInfo {

    private String name;
    private String phone;
    private String city;
    private String url;

}
