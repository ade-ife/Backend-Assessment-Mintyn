package Niji.Backend.Assessment.Mintyn.Controller;

import Niji.Backend.Assessment.Mintyn.Dtos.LoginDto;
import Niji.Backend.Assessment.Mintyn.Pojo.LoginResponse;
import Niji.Backend.Assessment.Mintyn.Dtos.SignupDto;
import Niji.Backend.Assessment.Mintyn.Pojo.SignupResponse;
import Niji.Backend.Assessment.Mintyn.Service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequestMapping(value = "v1/api/authentication", produces = APPLICATION_JSON_VALUE)
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/signup")
    public SignupResponse signUp(@Valid @RequestBody SignupDto signupDto) {
        return authenticationService.signUp(signupDto);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

}
