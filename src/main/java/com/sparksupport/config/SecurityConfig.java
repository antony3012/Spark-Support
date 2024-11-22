package com.sparksupport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       return  http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
//               .requestMatchers("/api/products/*").permitAll()
                 .requestMatchers("/api/products/addProduct").hasRole("ADMIN")
                 .requestMatchers("/api/products/updateProduct/{id}").hasRole("ADMIN")
                 .requestMatchers("/api/products/removeProduct/{id}").hasRole("ADMIN")
                 .anyRequest().authenticated())
//               .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();

    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.builder()
                .username("antony")
                .password(passwordEncoder().encode("ant@123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("mark")
                .password(passwordEncoder().encode("mark@123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin,user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
