package Niji.Backend.Assessment.Mintyn.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchemeVerificationModel {
    private String type;
    private String bank;
    private String scheme;
}
