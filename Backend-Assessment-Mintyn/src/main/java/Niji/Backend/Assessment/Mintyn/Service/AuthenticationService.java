package Niji.Backend.Assessment.Mintyn.Service;

import Niji.Backend.Assessment.Mintyn.Dtos.LoginDto;
import Niji.Backend.Assessment.Mintyn.Pojo.LoginResponse;
import Niji.Backend.Assessment.Mintyn.Dtos.SignupDto;
import Niji.Backend.Assessment.Mintyn.Pojo.SignupResponse;

public interface AuthenticationService {

    SignupResponse signUp(SignupDto signupDto);

    LoginResponse login(LoginDto loginDto);
}
