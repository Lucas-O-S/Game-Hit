package br.edu.cefsa.gametracker.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.edu.cefsa.gametracker.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/error",
                "/favicon.ico",
                "/js/**",          // liberar scripts
                "/css/**",         // liberar css
                "/images/**",      // ajustar conforme sua pasta de imagens (ex: /IMG/**)
                "/IMG/**",
                "/h2-console/**"   // liberar console H2
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/index", "/Usuario/Login", "/Usuario/Cadastro", "/Usuario/Autenticar", "/h2-console/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/Usuario/Salvar").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/Usuario/Login")
            .loginProcessingUrl("/Usuario/Autenticar")
            .defaultSuccessUrl("/index", true)
            .failureUrl("/Usuario/Login?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout") // aqui define que o logout será pela URL /logout
            .logoutSuccessUrl("/") // redireciona após logout
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        )
        .headers(headers -> headers.frameOptions().sameOrigin()) // Pra permitir H2 console em iframe
        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); // Desabilitar CSRF para H2 console


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

    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


}
