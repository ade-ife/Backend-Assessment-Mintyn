package Niji.Backend.Assessment.Mintyn.service;

import Niji.Backend.Assessment.Mintyn.Repository.CardEntityRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CardServiceImplTest.class)
@Import(CardEntityRepository.class)
public class CardServiceImplTest {
}
