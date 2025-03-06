package com.wjcarpi.seguridad;

import com.wjcarpi.servicio.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    // Constructor
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    // Configuración del codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración del proveedor de autenticación
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Configuración del AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuración de la cadena de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF (en ciertos casos)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/verify_code","/recover_password","/register", "/login", "/register_admin", "/register_carpintero", "/resources/**", "/static/**").permitAll() // Permite acceso público a estas URLs
                .requestMatchers("/Administrador/**").hasRole("ADMIN") // Solo accesible para administradores
                .requestMatchers("/Usuarios/**").hasRole("USER")   // Solo accesible para usuarios
                .requestMatchers("/Carpintero/**").hasRole("CARPINTERO") // Solo accesible para carpinteros
                .anyRequest().authenticated() // Requiere autenticación para otras URLs
            )
            .formLogin(form -> form
                .loginPage("/login")  // Página de login
                .loginProcessingUrl("/login") // URL para procesar el login
                .failureUrl("/login?error=true") // URL en caso de error de login
                .successHandler(customAuthenticationSuccessHandler) // Redirige según el rol con el handler personalizado
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL de logout
                .logoutSuccessUrl("/login?logout=true") // Redirige a login después de cerrar sesión
                .permitAll()
            );

        return http.build();
    }
}
