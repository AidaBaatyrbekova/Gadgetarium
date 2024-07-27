package com.peaksoft.gadgetarium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class FavoriteConfig {

    @Bean(name = "favoriteSecurityFilterChain")
    public SecurityFilterChain favoriteSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/favorites/**").authenticated()
                                .anyRequest().permitAll()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean(name = "favoriteUserDetailsService")
    public UserDetailsService favoriteUserDetailsService() {
        UserDetails user = User.withUsername("user")
                .password(favoritePasswordEncoder().encode("password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean(name = "favoritePasswordEncoder")
    public PasswordEncoder favoritePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
