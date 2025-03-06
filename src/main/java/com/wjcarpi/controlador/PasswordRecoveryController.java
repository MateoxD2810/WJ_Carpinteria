package com.wjcarpi.controlador;

import com.wjcarpi.servicio.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PasswordRecoveryController {

    @Autowired
    private UserService userService;

    @GetMapping("/recover_password")
    public String showRecoveryPage() {
        return "recover_password";
    }

    @PostMapping("/recover_password")
    public String sendRecoveryCode(@RequestParam("email") String email, Model model) {
        try {
            // Genera y envía el código de recuperación al correo
            String recoveryCode = userService.recuperarContrasena(email);

            // Mensaje de éxito
            model.addAttribute("message", "Se ha enviado un código de recuperación a tu correo: " + recoveryCode);
        } catch (RuntimeException e) {
            // Manejo de errores
            model.addAttribute("error", e.getMessage());
        }
        return "recover_password";
    }

    @GetMapping("/verify_code")
    public String showVerifyCodePage() {
        return "verify_code";
    }

    @PostMapping("/verify_code")
    public String verifyCodeAndChangePassword(@RequestParam("code") String code, @RequestParam("newPassword") String newPassword, Model model) {
        boolean success = userService.verifyRecoveryCode(code, newPassword);
        if (success) {
            model.addAttribute("message", "Contraseña actualizada con éxito.");
            return "login"; // Redirigir al login
        } else {
            model.addAttribute("error", "Código inválido o expirado.");
            return "verify_code";
        }
    }
}
