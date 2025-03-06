package com.wjcarpi.controlador;

import com.wjcarpi.servicio.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.wjcarpi.modelo.User;
import com.wjcarpi.modelo.Role;
import com.wjcarpi.repositorio.UserRepository;
import org.springframework.ui.Model;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    //CARPINTEROS

@GetMapping("/Administrador/NuevoCarpintero")
public String mostrarFormularioNuevoCarpintero(Model model) {
    model.addAttribute("carpintero", new User());
    return "Administrador/NuevoCarpintero";
}

@PostMapping("/Administrador/NuevoCarpintero")
public String crearCarpintero(@ModelAttribute User carpintero) {
    carpintero.setPassword(passwordEncoder.encode(carpintero.getPassword()));
    Role rolCarpintero = new Role();
    rolCarpintero.setId(3); // ID del Carpintero
    carpintero.setRole(rolCarpintero);
    userService.guardarUsuario(carpintero);
    return "redirect:/Administrador/Carpinteros";
}

@GetMapping("/Administrador/ActualizarCarpintero/{id}")
public String mostrarFormularioActualizarCarpintero(@PathVariable Long id, Model model) {
    User carpintero = userService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Carpintero no encontrado"));
    model.addAttribute("carpintero", carpintero);
    return "Administrador/ActualizarCarpintero";
}

// Actualizar Carpintero
@PostMapping("/Administrador/ActualizarCarpintero")
public String actualizarCarpintero(@ModelAttribute User carpintero) {
    userService.actualizarUsuario(carpintero);
    return "redirect:/Administrador/Carpinteros";
}

// Eliminar Carpintero
@PostMapping("/Administrador/EliminarCarpintero/{id}")
public String eliminarCarpintero(@PathVariable Long id) {
    userService.eliminarUsuario(id);
    return "redirect:/Administrador/Carpinteros";
}


@GetMapping("/Administrador/Actualizar/{id}")
    public String mostrarFormularioActualizarAdmin(@PathVariable Long id, Model model) {
        User admin = userService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        model.addAttribute("admin", admin);
        return "Administrador/ActualizarAdministrador";
    }


    //CARPINTEROS


    @GetMapping("/Administrador/Actualizar")
    public String Actualizar() {
        return "Administrador/Actualizar";
    }

    @GetMapping("/Administrador/Carpinteros")
    public String listarCarpinteros(Model model) {
        model.addAttribute("carpinteros", userRepository.findByRoleName("ROLE_CARPINTERO")); 
        return "Administrador/Carpinteros";
    }


    @GetMapping("/Administrador/Clientes")
    public String mostrarUsuarios(Model model) {
        model.addAttribute("users", userRepository.findByRoleName("ROLE_USER"));
        return "Administrador/Clientes";
    }

    @GetMapping("/Administrador/Comentarios")
    public String Comentarios() {
        return "Administrador/Comentarios";
    }

    @GetMapping("/Administrador/Envios")
    public String Envios() {
        return "Administrador/Envios";
    }


    @GetMapping("/Administrador/PedirMaterial")
    public String PerdirMaterial() {
        return "Administrador/PedirMaterial";
    }

    @GetMapping("/Administrador/Proveedores")
    public String Proveedores() {
        return "Administrador/Proveedores";
    }

    @GetMapping("/Administrador/Ventas")
    public String Ventas() {
        return "Administrador/Ventas";
    }

    @GetMapping("/Administrador/VistaRapida")
    public String VistaRapida() {
        return "Administrador/VistaRapida";
    }

}
