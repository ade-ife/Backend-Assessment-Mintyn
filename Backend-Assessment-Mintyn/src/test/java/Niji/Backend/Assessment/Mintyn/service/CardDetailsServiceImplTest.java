package Niji.Backend.Assessment.Mintyn.service;

import Niji.Backend.Assessment.Mintyn.Config.CacheManager;
import Niji.Backend.Assessment.Mintyn.Dtos.CardStatisticsResponse;
import Niji.Backend.Assessment.Mintyn.Dtos.VerifyCardDetailsDto;
import Niji.Backend.Assessment.Mintyn.Entities.CardEntity;
import Niji.Backend.Assessment.Mintyn.Pojo.SchemeVerificationModel;
import Niji.Backend.Assessment.Mintyn.Repository.CardEntityRepository;
import Niji.Backend.Assessment.Mintyn.Security.DefaultUserDetailsService;
import Niji.Backend.Assessment.Mintyn.Security.TokenAuthenticationService;
import Niji.Backend.Assessment.Mintyn.Service.VerificationService;
import Niji.Backend.Assessment.Mintyn.Service.impl.AuthenticationServiceImpl;
import Niji.Backend.Assessment.Mintyn.Service.impl.CardDetailsServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import reactor.core.publisher.Mono;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CardDetailsServiceImplTest.class)
@Import(CardEntityRepository.class)
public class CardDetailsServiceImplTest {

    @MockBean
    private CardEntityRepository cardEntityRepository;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private VerificationService verificationService;

    @MockBean
    private TokenAuthenticationService tokenAuthenticationService;


    @MockBean
    private DefaultUserDetailsService userDetailsService;

    @InjectMocks
    private CardDetailsServiceImpl cardDetailsService;

    @BeforeEach
    void setUp() {
        cardDetailsService = new CardDetailsServiceImpl(cardEntityRepository, cacheManager, verificationService);
    }


    @Test
    void getCardStatistics_ShouldReturnCardStatistics() {
        // Given
        int start = 0;
        int limit = 10;

        // Creating mock data
        CardEntity cardEntity1 = new CardEntity();
        cardEntity1.setBin("123");
        cardEntity1.setCount(5);

        CardEntity cardEntity2 = new CardEntity();
        cardEntity2.setBin("456");
        cardEntity2.setCount(3);

        Page<CardEntity> mockPage = new PageImpl<>(List.of(cardEntity1, cardEntity2));


        when(cardEntityRepository.findAll(ArgumentMatchers.<Pageable>any())).thenReturn(mockPage);

        // When
        CardStatisticsResponse result = cardDetailsService.getCardStatistics(start, limit);

        // Then
        assertEquals(2, result.getSize());
        assertEquals(start, result.getStart());
        assertEquals(limit, result.getLimit());
        assertEquals(5, result.getData().get("123"));
        assertEquals(3, result.getData().get("456"));
        verify(cardEntityRepository).findAll(ArgumentMatchers.<Pageable>any());

    }

    @Test
    void validateCard_ShouldReturnSchemeVerificationModel() {
        // Given
        VerifyCardDetailsDto cardDetailsDto = new VerifyCardDetailsDto("123456");
        SchemeVerificationModel mockModel = new SchemeVerificationModel("debit", "Test Bank", "visa");

        when(cacheManager.get(anyString(), eq(SchemeVerificationModel.class))).thenReturn(Mono.empty());
        when(cardEntityRepository.findByBin(anyString())).thenReturn(Optional.empty());
        when(verificationService.verifyScheme(anyString())).thenReturn(Mono.just(mockModel));
        doNothing().when(cacheManager).put(anyString(), any());

        // When
        SchemeVerificationModel result = cardDetailsService.validateCard(cardDetailsDto).block();

        // Then
        assertNotNull(result);
        assertEquals("debit", result.getType());
        assertEquals("Test Bank", result.getBank());
        assertEquals("visa", result.getScheme());

        // Verify method invocations
        verify(cacheManager).get(anyString(), eq(SchemeVerificationModel.class));
        verify(cardEntityRepository, times(2)).findByBin(anyString());
        verify(verificationService).verifyScheme(anyString());
        verify(cacheManager).put(anyString(), any());
    }


}


