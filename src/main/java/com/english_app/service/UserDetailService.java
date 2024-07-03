package com.english_app.service;

import com.english_app.dto.CustomUser;
import com.english_app.entity.Role;
import com.english_app.entity.User;
import com.english_app.entity.Userrole;
import com.english_app.enums.RoleEnum;
import com.english_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return CustomUser.builder()
                .user(user)
                .build();
    }
    //    OAuth2 Google
    public void loginFromOauth2(OAuth2AuthenticationToken authentication) {
        CustomUser customUser = CustomUser.builder()
                .user(this.getUser(authentication))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    User getUser(OAuth2AuthenticationToken authentication) {
        return User.builder()
                .username(authentication.getPrincipal().getAttribute("email"))
                .email(authentication.getPrincipal().getAttribute("email"))
                .avatar(authentication.getPrincipal().getAttribute("picture"))
                .fullName(authentication.getPrincipal().getAttribute("name"))
                .password(Long.toHexString(System.currentTimeMillis()))
                .userroles(this.getUserRoles())
                .build();
    }

    Set<Userrole> getUserRoles() {
        return Set.of(this.getUserRole());
    }

    Userrole getUserRole() {
        return Userrole.builder()
                .role(this.getRole())
                .build();
    }

    private Role getRole() {
        return Role.builder()
                .name(RoleEnum.USER.name())
                .build();
    }
}
