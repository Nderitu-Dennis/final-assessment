package tech.csm.final_assmnt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.csm.final_assmnt.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //no sessions, tokens are being used
                )
//                authorization rules
                .authorizeHttpRequests(auth -> auth
                        //  public gates dont need token
                        .requestMatchers(
                                "/",
                               "/login",
                                "/api/login").permitAll()

                        //view, address bar url's
                        .requestMatchers("/*jsp",
                                "/dashboard",
                                "/employees",
                                "/projects",
                                "/assign",
                                "/my-projects").permitAll()

                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll() //any other request to go to , jwtfilter  handling
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}