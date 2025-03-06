package com.wjcarpi.controlador;

import com.wjcarpi.servicio.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BulkEmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send_bulk_email")
    public String sendBulkEmail() {
        String subject = "¡Los descuentos llegan en ANTES DE LA SUSTENTACIÓN!";
        String message = "¡Acabas de recibir un 20% de descuento en productos de WJ Carpintería!";

        // Aquí puedes poner la lista de correos a los que deseas enviar el mensaje
        String[] recipients = {
            "mateogalindosandino@gmail.com ",
            "juancamilosus580@gmail.com",
            "danielgamer24161@gmail.com"
            // Añade más correos según sea necesario
        };

        // Llamar al servicio para enviar el correo masivo
        emailService.sendBulkEmail(recipients, subject, message);

        // Puedes agregar un mensaje de éxito si lo deseas
        return "redirect:/Administrador/Index"; // Redirigir a alguna página de confirmación
    }
}
