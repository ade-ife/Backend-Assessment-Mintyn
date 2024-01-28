package Niji.Backend.Assessment.Mintyn.Security;



import Niji.Backend.Assessment.Mintyn.Pojo.UserAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenAuthenticationService tokenAuthenticationService;

    public AuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        super();
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain chain) throws IOException, ServletException {

        try {
            Optional<UserAuthentication> authentication = tokenAuthenticationService.getAuthentication(httpServletRequest);

            if (authentication.isPresent()) {
                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        authentication.get().getDetails(),
                        null,
                        authentication.get().getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            chain.doFilter(httpServletRequest, httpServletResponse);
        } catch (AuthenticationException e) {
            logger.error("Authentication error: " + e.getMessage(), e);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().write("Authentication error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error: " + e.getMessage(), e);
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.getWriter().write("Internal server error: " + e.getMessage());
        }
    }
}

