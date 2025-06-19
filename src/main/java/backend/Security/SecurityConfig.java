package backend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
                        throws Exception {
                final String DIRECCION_ANGULAR = "http:localhost:4200";
                http
                                .csrf(csrf -> csrf.disable()) // Desactivar CSRF para APIs
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("autonomax/**", DIRECCION_ANGULAR,
                                                                "/swagger-ui/**", "/v3/api-docs/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Sin
                                                                                                          // sesiones
                                )
                                .formLogin(form -> form.disable()) // desactiva formulario por defecto
                                .httpBasic(basic -> basic.disable()); // deactiva autenticación básica

                return http.build();
        }
}
