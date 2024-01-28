package Niji.Backend.Assessment.Mintyn.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Builder
@Data
public class VerifyCardDetailsDto {

    @NotEmpty(message = "bin is required")
    private String bin;
    private String provider;
}
