package Niji.Backend.Assessment.Mintyn.controller;

import Niji.Backend.Assessment.Mintyn.Controller.AuthenticationController;
import Niji.Backend.Assessment.Mintyn.Dtos.LoginDto;
import Niji.Backend.Assessment.Mintyn.Dtos.SignupDto;
import Niji.Backend.Assessment.Mintyn.Entities.UserEntity;
import Niji.Backend.Assessment.Mintyn.Pojo.LoginResponse;
import Niji.Backend.Assessment.Mintyn.Pojo.SignupResponse;
import Niji.Backend.Assessment.Mintyn.Repository.UserEntityRepository;
import Niji.Backend.Assessment.Mintyn.Security.DefaultUserDetailsService;
import Niji.Backend.Assessment.Mintyn.Security.JwtGenerator;
import Niji.Backend.Assessment.Mintyn.Security.TokenAuthenticationService;
import Niji.Backend.Assessment.Mintyn.Service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {


    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private TokenAuthenticationService tokenService;

    @MockBean
    private DefaultUserDetailsService userDetailsService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private  PasswordEncoder passwordEncoder;

    @MockBean
    private  JwtGenerator jwtGenerator;
    @InjectMocks
    private AuthenticationController authController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSignUp() throws Exception {
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUser");
        signupDto.setPassword("testPassword");

        SignupResponse expectedResponse = new SignupResponse();
        expectedResponse.setResponseCode("00");
        expectedResponse.setResponseDescription("SUCCESS");
        expectedResponse.setDescription("USER REGISTERED SUCCESSFULLY");


        when(authenticationService.signUp(any(SignupDto.class))).thenReturn(expectedResponse);


        mockMvc.perform(post("/v1/api/authentication/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signupDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value("00"))
                .andExpect(jsonPath("$.responseDescription").value("SUCCESS"))
                .andExpect(jsonPath("$.description").value("USER REGISTERED SUCCESSFULLY"));
    }

    @Test
    public void testLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testUser");
        loginDto.setPassword("testPassword");


        UserEntity userEntity =  userEntityRepository.save(UserEntity.builder().
                username(loginDto.getUsername())
                .password(passwordEncoder.encode(loginDto.getPassword()))
                .build());
        userEntityRepository.save(userEntity);


        UserEntity testUser = userEntityRepository.findFirstByUsername(loginDto.getUsername()).orElse(null);


        LoginResponse expectedResponse = new LoginResponse();
        expectedResponse.setResponseCode("00");
        expectedResponse.setResponseDescription("SUCCESS");
        expectedResponse.setDescription("LOGIN SUCCESSFUL");
        expectedResponse.setUsername("testUser");


        String token = jwtGenerator.generateToken(testUser);
        expectedResponse.setToken(token);

        expectedResponse.setExpiryDate(LocalDateTime.parse("2024-01-29T00:14:29.683"));


        when(authenticationService.login(any(LoginDto.class))).thenReturn(expectedResponse);


        mockMvc.perform(post("/v1/api/authentication/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value("00"))
                .andExpect(jsonPath("$.responseDescription").value("SUCCESS"))
                .andExpect(jsonPath("$.description").value("LOGIN SUCCESSFUL"))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.expiryDate").value("2024-01-29T00:14:29.683"));
    }



}
