package Niji.Backend.Assessment.Mintyn.Pojo;

import Niji.Backend.Assessment.Mintyn.Pojo.Response.ResponseObject;
import Niji.Backend.Assessment.Mintyn.Pojo.VerificationPojos.BankInfo;
import Niji.Backend.Assessment.Mintyn.Pojo.VerificationPojos.CountryInfo;
import Niji.Backend.Assessment.Mintyn.Pojo.VerificationPojos.NumberInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class CardResponse extends ResponseObject {
    private String brand;
    private boolean prepaid;
    private String type;
    private NumberInfo number;
    private String scheme;
    private BankInfo bank;
    private CountryInfo country;


}
