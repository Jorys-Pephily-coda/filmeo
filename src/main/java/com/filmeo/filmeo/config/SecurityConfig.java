package com.filmeo.filmeo.config;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//import com.model.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    //@Autowired
    //private CustomUserDetailsService userDetailsService;

    private String uniqueKey = "CodaSchool";

    @Bean
    PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**").permitAll() // Public routes
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                //.requestMatchers("").hasAnyRole("ADMIN")
                                                //.requestMatchers("").hasRole("")
                                                //.anyRequest().authenticated()  // all others route needs auth
                                                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())                               // nécessary for the login form
            .formLogin(form -> form.loginPage("/login")
                                    .defaultSuccessUrl("/", false)      // true force la redirection dans tous les cas
                                                                        // false rediriger vers la page demandée avec querystring ?continue
                                    .permitAll()
            )
            .logout(logout -> logout.logoutUrl("/logout")
                                    .logoutSuccessUrl("/")
                                    .invalidateHttpSession(true)    // invalidate the session
                                    .deleteCookies("JSESSIONID")    // delete client cookie
                                    .permitAll()
            )
            .sessionManagement(session -> session.maximumSessions(5))  // 5 sessions per user
            .rememberMe(remember -> remember.key(uniqueKey)
                                            .tokenValiditySeconds(86400 * 365)  // lenght in seconds
                                            .rememberMeParameter("remember-me") // name of the champ in the form
            )
        ;
        return http.build();
    }


    /*@Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }*/
}
