package Niji.Backend.Assessment.Mintyn.Pojo.VerificationPojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class NumberInfo {
    private int length;
    private boolean luhn;
}
