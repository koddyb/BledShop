package com.soft.MarketPlace.securityConfiguration;

import com.soft.MarketPlace.service.ClientService;
import com.soft.MarketPlace.service.UserService;
import com.soft.MarketPlace.service.UtilisateurService;
import jakarta.annotation.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public AuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }


    @Bean
    public SecurityFilterChain userfilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/client/inscription/**").permitAll()
                        .requestMatchers("/client/form/password").permitAll()
                        .requestMatchers("/client/recuperer").permitAll()
                        .requestMatchers("/utilisateur/form/password").permitAll()
                        .requestMatchers("/utilisateur/recuperer").permitAll()
                        .requestMatchers("/client/telephone**").authenticated()
                        .requestMatchers("/client/modifier/**").authenticated()
                        .requestMatchers("/utilisateur/inscription/**").permitAll()
                        .requestMatchers("/utilisateur/commande/**").authenticated()
                        .requestMatchers("/utilisateur/paiement/**").authenticated()
                        .requestMatchers("/utilisateur/**").authenticated()
                        .requestMatchers("/client/**").authenticated()
                        .requestMatchers("/utilisateur/inscription/confirm").permitAll()
                        .requestMatchers("/client/**").hasRole("CLIENT")
                        .requestMatchers("/utilisateur/**").hasRole("UTILISATEUR")
                        .anyRequest().permitAll()
                )
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error=loginError")
                .successForwardUrl("/home")
                .defaultSuccessUrl("/home")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .csrf().disable();
        return http.build();
    }
}
