package com.filmeo.filmeo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.filmeo.filmeo.model.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private static final String REMEMBER_ME_KEY = "filmeo-unique-secret-key-2026";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // public routes
                .requestMatchers("/", "/home", "/login", "/register", "/do-login", "/do-register", "/connexion", "/inscription").permitAll()
                .requestMatchers("/productions/**", "/artists/**", "/search").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                .requestMatchers("/profile/**", "/watchlist/**", "/reviews/**").hasAnyRole("USER", "ADMIN")
                
                .anyRequest().authenticated()
            )
            
            // Configuration CSRF (désactivé pour simplifier - à activer en prod)
            .csrf(csrf -> csrf.disable())
            
            // login form
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/do-login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
            )
            
            // logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            // session management
            .sessionManagement(session -> session
                .maximumSessions(3)
                .maxSessionsPreventsLogin(false)  // false = disconnect old session
            )
            
            // Remember Me
            .rememberMe(remember -> remember
                .key(REMEMBER_ME_KEY)
                .tokenValiditySeconds(86400 * 30)  // 30 days
                .rememberMeParameter("remember-me")
                /*.userDetailsService(userDetailsService)*/
            )
        ;
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }
}