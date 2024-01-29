//package Niji.Backend.Assessment.Mintyn.service;
//
//import Niji.Backend.Assessment.Mintyn.Dtos.LoginDto;
//import Niji.Backend.Assessment.Mintyn.Dtos.SignupDto;
//import Niji.Backend.Assessment.Mintyn.Entities.TokenEntity;
//import Niji.Backend.Assessment.Mintyn.Entities.UserEntity;
//import Niji.Backend.Assessment.Mintyn.Exception.CustomException;
//import Niji.Backend.Assessment.Mintyn.Pojo.LoginResponse;
//import Niji.Backend.Assessment.Mintyn.Pojo.Response.EnumResponseCodes;
//import Niji.Backend.Assessment.Mintyn.Pojo.SignupResponse;
//import Niji.Backend.Assessment.Mintyn.Repository.UserEntityRepository;
//import Niji.Backend.Assessment.Mintyn.Security.TokenAuthenticationService;
//import Niji.Backend.Assessment.Mintyn.Service.AuthenticationService;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//public class AuthenticationServiceImplTest {
//
//  @Autowired
//    private UserEntityRepository userRepository;
//
//    @MockBean
//    private UserEntity userEntity;
//    @MockBean
//    private TokenAuthenticationService tokenService;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    @MockBean
//    private EnumResponseCodes enumResponseCodes;
//
//    @MockBean
//    private CustomException customException;
//
//    @InjectMocks
//    private AuthenticationService authenticationService;
//
//    @Test
//    void signUp_Successful() {
//        SignupDto signupDto = new SignupDto("newUser", "password");
//        SignupResponse expectedResponse = new SignupResponse("00", EnumResponseCodes.SUCCESS.toString(), "USER REGISTERED SUCCESSFULLY");
//
//        when(userRepository.existsUserEntityByUsername(signupDto.getUsername())).thenReturn(false);
//        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        SignupResponse actualResponse = authenticationService.signUp(signupDto);
//
//        assertEquals(expectedResponse, actualResponse);
//        verify(userRepository, times(1)).existsUserEntityByUsername(signupDto.getUsername());
//        verify(userRepository, times(1)).save(any(UserEntity.class));
//    }
//
//    @Test
//    void signUp_UserAlreadyExists() {
//        SignupDto signupDto = new SignupDto("existingUser", "password");
//        SignupResponse expectedResponse = new SignupResponse("96", EnumResponseCodes.FAILED.toString(), "USER ALREADY EXISTS WITH USERNAME");
//
//        when(userRepository.existsUserEntityByUsername(signupDto.getUsername())).thenReturn(true);
//
//        SignupResponse actualResponse = authenticationService.signUp(signupDto);
//
//        assertEquals(expectedResponse, actualResponse);
//        verify(userRepository, times(1)).existsUserEntityByUsername(signupDto.getUsername());
//        verify(userRepository, never()).save(any(UserEntity.class));
//    }
//
////    @Test
////    void login_Successful() {
////        LoginDto loginDto = new LoginDto("existingUser", "password");
////        UserEntity user = new UserEntity("existingUser", "hashedPassword");
////        TokenEntity tokenEntity = new TokenEntity("testToken", LocalDateTime.now().plusHours(50));
////
////        when(userRepository.findFirstByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
////        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
////        when(tokenService.generateToken(user)).thenReturn(tokenEntity);
////
////        LoginResponse expectedResponse = new LoginResponse("00", EnumResponseCodes.SUCCESS.toString(), "LOGIN SUCCESSFUL",
////                user.getUsername(), "testToken", tokenEntity.getExpiredTime());
////
////        LoginResponse actualResponse = authenticationService.login(loginDto);
////
////        assertEquals(expectedResponse, actualResponse);
////        verify(userRepository, times(1)).save(user);
////        verify(tokenService, times(1)).generateToken(user);
////    }
//
//    @Test
//    void login_InvalidUsernameOrPassword() {
//        LoginDto loginDto = new LoginDto("nonExistingUser", "wrongPassword");
//
//        when(userRepository.findFirstByUsername(loginDto.getUsername())).thenReturn(Optional.empty());
//
//        assertThrows(CustomException.class, () -> authenticationService.login(loginDto));
//        verify(userRepository, never()).save(any(UserEntity.class));
//        verify(tokenService, never()).generateToken(any(UserEntity.class));
//    }
//}
