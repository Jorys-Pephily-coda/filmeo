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
            // IMPORTANT: Spring Security authorization configuration
            // This defines which URLs require which roles/authorities to access
            .authorizeHttpRequests(auth -> auth
                // PUBLIC ROUTES: Anyone can access these (authenticated or not)
                .requestMatchers("/", "/home", "/login", "/register", "/do-login", "/do-register", "/connexion", "/inscription").permitAll()
                .requestMatchers("/productions/**", "/artists/**", "/search").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                
                // ADMIN ROUTES: Only users with ADMIN role can access
                // NOTE: hasRole("ADMIN") looks for "ROLE_ADMIN" in the authorities list
                // If your admin account stores role as ["ADMIN", "USER"], Spring Security 
                // automatically prefixes "ROLE_" making it ["ROLE_ADMIN", "ROLE_USER"]
                // Make sure your ConnectedUser.getAuthorities() returns authorities with "ROLE_" prefix
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // USER ROUTES: Users with either USER or ADMIN role can access
                // This uses hasAnyRole() which checks for multiple roles
                .requestMatchers("/profile/**", "/watchlist/**", "/reviews/**").hasAnyRole("USER", "ADMIN")
                
                // CATCH-ALL: Any other request requires authentication (must be logged in)
                // This doesn't check roles, just that user is authenticated
                .anyRequest().authenticated()
            )
            
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
                .userDetailsService(userDetailsService)
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