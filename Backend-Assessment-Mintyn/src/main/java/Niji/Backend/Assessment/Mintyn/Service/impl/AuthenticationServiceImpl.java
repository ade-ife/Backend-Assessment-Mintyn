package Niji.Backend.Assessment.Mintyn.Service.impl;

import Niji.Backend.Assessment.Mintyn.Entities.TokenEntity;
import Niji.Backend.Assessment.Mintyn.Entities.UserEntity;
import Niji.Backend.Assessment.Mintyn.Exception.CustomException;
import Niji.Backend.Assessment.Mintyn.Dtos.LoginDto;
import Niji.Backend.Assessment.Mintyn.Pojo.LoginResponse;
import Niji.Backend.Assessment.Mintyn.Pojo.Response.EnumResponseCodes;
import Niji.Backend.Assessment.Mintyn.Dtos.SignupDto;
import Niji.Backend.Assessment.Mintyn.Pojo.SignupResponse;
import Niji.Backend.Assessment.Mintyn.Repository.UserEntityRepository;
import Niji.Backend.Assessment.Mintyn.Security.TokenAuthenticationService;
import Niji.Backend.Assessment.Mintyn.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserEntityRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final TokenAuthenticationService tokenService;

    @Override
    public SignupResponse signUp(SignupDto signupDto) {
        SignupResponse response = new SignupResponse();

        try {
            if (userRepository.existsUserEntityByUsername(signupDto.getUsername())) {
                response.setResponseCode("96");
                response.setResponseDescription(EnumResponseCodes.FAILED.toString());
                response.setDescription("USER ALREADY EXISTS WITH USERNAME");
                return response;
            }
                UserEntity userEntity =   userRepository.save(UserEntity.builder().
                        username(signupDto.getUsername())
                        .password(passwordEncoder.encode(signupDto.getPassword()))
                        .build());
                userRepository.save(userEntity);

                response.setResponseCode("00");
                response.setResponseDescription(EnumResponseCodes.SUCCESS.toString());
                response.setDescription("USER REGISTERED SUCCESSFULLY");
                return response;
            }


        catch (Exception e){
            log.info("Signup User error "+ e);
        }

        return null;
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {
        LoginResponse response = new LoginResponse();

        UserEntity user = getUser(loginDto.getUsername());
       if(verifyPassword(loginDto.getPassword(), user.getPassword(),user)  == false)
        {
            response.setResponseCode("96");
            response.setResponseDescription(EnumResponseCodes.FAILED.toString());
            response.setDescription("INVALID USERNAME OR PASSWORD");
            return response;
        }

        userRepository.save(user);

        TokenEntity token = tokenService.generatorToken(user);

        response.setResponseCode("00");
        response.setResponseDescription(EnumResponseCodes.SUCCESS.toString());
        response.setDescription("LOGIN SUCCESSFUL");
        response.setUsername(user.getUsername());
        response.setToken(token.getToken());
        response.setExpiryDate(token.getExpiredTime());
        return response;
    }

    private UserEntity getUser(String username) {
        Optional<UserEntity> optionalUser = userRepository.findFirstByUsername(username);
        if (optionalUser.isEmpty()){
            throw wrongDetailsException();
        }
        UserEntity user = optionalUser.get();

        return user;
    }


    private boolean verifyPassword(String password, String encryptedPassword,UserEntity user) {
        if (!passwordEncoder.matches(password, encryptedPassword)) {
            userRepository.save(user);
            return false;
        }
        return true;
    }

    private CustomException wrongDetailsException() {
        return new CustomException("Wrong username or password entered");
    }

}
