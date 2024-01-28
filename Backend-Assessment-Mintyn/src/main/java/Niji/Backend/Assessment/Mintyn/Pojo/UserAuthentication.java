package Niji.Backend.Assessment.Mintyn.Pojo;


import Niji.Backend.Assessment.Mintyn.Entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class UserAuthentication implements Authentication {

    private static final long serialVersionUID = 3354675843563256712L;
    private final UserEntity user;
    private boolean authenticated = true;

    public UserAuthentication(UserEntity user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public Object getCredentials() {
        return null;
    }


    @Override
    public UserEntity getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
