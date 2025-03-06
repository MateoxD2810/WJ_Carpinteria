package com.wjcarpi.controlador;

import com.wjcarpi.modelo.User;
import com.wjcarpi.modelo.Role;
import com.wjcarpi.repositorio.UserRepository;
import com.wjcarpi.repositorio.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // Página de login para el usuario
    }

    // Página de registro (usuario)
    @GetMapping("/register")
    public String register() {
        return "register"; // Página de registro para el usuario
    }

    // Procesar registro (usuario)
    @PostMapping("/register")
    public String processRegister(User user, Model model) {
        // Verificar si el nombre de usuario ya existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("errorMessage", "El nombre de usuario ya está en uso.");
            return "register";
        }

        // Verificar si el correo electrónico ya existe
        if (userRepository.findByGmail(user.getGmail()).isPresent()) {
            model.addAttribute("errorMessage", "El correo electrónico ya está en uso.");
            return "register";
        }

        // Codificar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Asignar el rol "ROLE_USER" (para usuario)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role USER no encontrado"));
        user.setRole(userRole);

        // Guardar el usuario
        userRepository.save(user);

        // Autenticación automática tras registro
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Redirigir al dashboard después del registro
        return "redirect:/Usuarios/Index";
    }

    // Página de registro (administrador)
    @GetMapping("/register_admin")
    public String registerAdmin() {
        return "register_admin"; // Página de registro para administrador
    }

    // Procesar registro (administrador)
    @PostMapping("/register_admin")
    public String processRegisterAdmin(User user, Model model) {
        // Verificar si el usuario ya existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("errorMessage", "El nombre de usuario ya está en uso.");
            return "register_admin";
        }

        // Codificar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Asignar el rol "ROLE_ADMIN" (para administrador)
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role ADMIN no encontrado"));
        user.setRole(adminRole);

        // Guardar el usuario
        userRepository.save(user);

        // Autenticación automática tras registro
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Redirigir al dashboard del administrador después del registro
        return "redirect:/Administrador/Index";
    }

    // Página de registro (carpintero)
    @GetMapping("/register_carpintero")
    public String registerCarpintero() {
        return "register_carpintero"; // Página de registro para carpintero
    }

    // Procesar registro (carpintero)
    @PostMapping("/register_carpintero")
    public String processRegisterCarpintero(User user, Model model) {
        // Verificar si el usuario ya existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("errorMessage", "El nombre de usuario ya está en uso.");
            return "register_carpintero";
        }

        // Codificar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Asignar el rol "ROLE_CARPINTERO" (para carpintero)
        Role carpinteroRole = roleRepository.findByName("ROLE_CARPINTERO")
                .orElseThrow(() -> new RuntimeException("Role CARPINTERO no encontrado"));
        user.setRole(carpinteroRole);

        // Guardar el usuario
        userRepository.save(user);

        // Autenticación automática tras registro
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Redirigir al dashboard del carpintero después del registro
        return "redirect:/Carpintero/Index";
    }

    // Dashboard de usuario
    @GetMapping("/Usuarios/Index")
    public String userDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Obtener el usuario autenticado
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("user", user);
        return "Usuarios/Index"; // Vista del dashboard de usuario
    }

    // Dashboard de administrador
    @GetMapping("/Administrador/Index")
    public String adminDashboard(Model model) {
        model.addAttribute("message", "Bienvenido al dashboard del administrador");
        return "Administrador/Index"; // Vista del dashboard de administrador
    }

    // Dashboard de carpintero
    @GetMapping("/Carpintero/Index")
    public String carpinteroDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Obtener el usuario autenticado
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Pasar el usuario autenticado al modelo
        model.addAttribute("user", user);

        return "Carpintero/Index"; // Vista del dashboard de carpintero
    }
}
