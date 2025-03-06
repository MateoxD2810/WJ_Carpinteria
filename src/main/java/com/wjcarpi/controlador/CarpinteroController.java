package com.wjcarpi.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarpinteroController {

    @GetMapping("/Carpintero/Actividad")
    public String Actividad() {
        return "Carpintero/Actividad";
    }

    @GetMapping("/Carpintero/Estadisticas")
    public String Estadisticas() {
        return "Carpintero/Estadisticas";
    }
    
    @GetMapping("/Carpintero/Materiales")
    public String Materiales() {
        return "Carpintero/Materiales";
    }

    @GetMapping("/Carpintero/Ordenes")
    public String Ordenes() {
        return "Carpintero/Ordenes";
    }

    @GetMapping("/Carpintero/Pendientes")
    public String Pendientes() {
        return "Carpintero/Pendientes";
    }

    @GetMapping("/Carpintero/Reseñas")
    public String Reseñas() {
        return "Carpintero/Reseñas";
    }

}
