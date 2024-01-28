package Niji.Backend.Assessment.Mintyn.Security;



import Niji.Backend.Assessment.Mintyn.Entities.UserEntity;
import Niji.Backend.Assessment.Mintyn.Repository.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userRepository;

    public DefaultUserDetailsService(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user =  userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("No user founds with username supplied : " + username);
        }
        return new CustomUserDetails(user);
    }
}