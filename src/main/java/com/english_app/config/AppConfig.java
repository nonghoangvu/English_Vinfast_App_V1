package com.english_app.config;

import com.english_app.enums.RoleEnum;
import com.english_app.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Autowired
    private UserDetailService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin").hasAuthority(RoleEnum.ADMIN.name())
                        .requestMatchers("/user").hasAnyAuthority(RoleEnum.ADMIN.name(), RoleEnum.USER.name())
                        .anyRequest().permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
                        .tokenValiditySeconds(10000)
                )
                .formLogin(form -> form.loginPage("/login").successHandler(this.authenticationSuccessHandler()))
                .userDetailsService(this.userDetailService)
                .exceptionHandling(ex -> ex.accessDeniedPage("/"));
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/oauth2/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/oauth2/login/success")
                .authorizationEndpoint(authorization -> authorization
                        .baseUri("/oauth2/authorize") // configure authorization endpoint
                )
        );
        return http.build();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(RoleEnum.ADMIN.name()))
                    ? "/admin"
                    : "/";
            response.sendRedirect(redirectUrl);
        };
    }
}
