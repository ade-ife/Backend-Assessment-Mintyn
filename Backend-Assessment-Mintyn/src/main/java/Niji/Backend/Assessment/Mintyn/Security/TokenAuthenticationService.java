package Niji.Backend.Assessment.Mintyn.Security;



import Niji.Backend.Assessment.Mintyn.Entities.TokenEntity;
import Niji.Backend.Assessment.Mintyn.Entities.UserEntity;
import Niji.Backend.Assessment.Mintyn.Pojo.UserAuthentication;
import Niji.Backend.Assessment.Mintyn.Repository.TokenEntityRepository;
import Niji.Backend.Assessment.Mintyn.Repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static java.util.Optional.empty;
@Service
public class TokenAuthenticationService {

    public static final String AUTH_HEADER_NAME = "Authorization";
    private final JwtGenerator jwtGenerator;
    private final UserEntityRepository userRepository;
    private final TokenEntityRepository tokenStoreRepository;
    private final Long expiration;

    @Autowired
    public TokenAuthenticationService(JwtGenerator jwtGenerator,
                                      UserEntityRepository userRepository,
                                      TokenEntityRepository tokenStoreRepository,
                                      @Value("${token.expiration}") Long expiration) {
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
        this.tokenStoreRepository = tokenStoreRepository;
        this.expiration = expiration;
    }

    public TokenEntity generatorToken(UserEntity user) {
        final String token = jwtGenerator.generateToken( user);
         return tokenStoreRepository.save(new TokenEntity(token, getExpiryDate()));
    }

    private LocalDateTime getExpiryDate() {
        return JwtGenerator.generateExpirationDate(expiration)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public Optional<UserAuthentication> getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null && tokenStoreRepository.findByToken(token).isPresent()) {
            String username = jwtGenerator.getUsernameFromToken(token);
            UserEntity user = userRepository.findFirstByUsername(username).orElse(null);
            if (user != null) {
                return Optional.of(new UserAuthentication(user));
            }
        }
        return empty();
    }
}

