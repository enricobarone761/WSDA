package it.unipa.wsda.progettocoffeecapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("GESTORE").implies("ADDETTO", "CLIENTE")
                .role("ADDETTO").implies("CLIENTE")
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/login-gestore", "/login-manutentore").permitAll()

                        .requestMatchers("/Cliente/**", "/CSS-e-Icone/**").permitAll()
                        .requestMatchers("/Addetto_manutenzione/**").permitAll()
                        .requestMatchers("/Gestore_sistema/**").permitAll()

                        .requestMatchers("/distributore/**", "/erogazione/**", "/sync").permitAll()

                        .requestMatchers("/credito", "/ricarica", "/connetti", "/disconnetti").hasRole("CLIENTE")
                        .requestMatchers("/connessione-distributore").hasRole("CLIENTE")

                        .requestMatchers("/info_distributori","/manutenzione/**").hasRole("ADDETTO")
                        .requestMatchers("/controllo-distributore").hasRole("ADDETTO")

                        .requestMatchers("/elenco-addetti","/gestione-addetti/**","/gestione-distributori/**").hasRole("GESTORE")

                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
