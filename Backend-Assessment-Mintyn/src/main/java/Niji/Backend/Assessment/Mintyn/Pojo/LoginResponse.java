package Niji.Backend.Assessment.Mintyn.Pojo;

import Niji.Backend.Assessment.Mintyn.Pojo.Response.ResponseObject;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponse extends ResponseObject {
    private String username;

    private String token;

    private LocalDateTime expiryDate;
}
