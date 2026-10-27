package br.study.SpringStudy_3.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.naming.AuthenticationException;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/auth").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/membro").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET,"/livro/**").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET,"/autor/**").permitAll())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        return http.build();
    }


    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
