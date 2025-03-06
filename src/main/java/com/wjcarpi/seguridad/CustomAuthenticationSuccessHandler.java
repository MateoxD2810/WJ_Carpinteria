package com.wjcarpi.seguridad;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String role = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .findFirst()
                .orElse("");

        String redirectUrl = "/defaultSuccessUrl"; // URL por defecto

        if (role.equals("ROLE_USER")) {
            redirectUrl = "/Usuarios/Index"; // Redirigir a dashboard de usuario
        } else if (role.equals("ROLE_ADMIN")) {
            redirectUrl = "/Administrador/Index"; // Redirigir a dashboard de administrador
        } else if (role.equals("ROLE_CARPINTERO")) {
            redirectUrl = "/Carpintero/Index"; // Redirigir a dashboard de carpintero
        }

        response.sendRedirect(redirectUrl); // Realiza la redirección
    }
}
