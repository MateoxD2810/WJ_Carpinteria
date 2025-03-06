package com.wjcarpi.controlador;

import com.wjcarpi.servicio.MuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuariosController {

    @Autowired
    private MuebleService muebleService;  // Inyección del servicio MuebleService

    @GetMapping("/Usuarios/ActualizarInfo")
    public String ActualizarInfo() {
        return "Usuarios/ActualizarInfo";
    }

    @GetMapping("/Usuarios/Carrito")
    public String Carrito() {
        return "Usuarios/Carrito";
    }

    @GetMapping("/Usuarios/Catalogo")
    public String Catalogo(Model model) {
        // Obtén todos los muebles desde el servicio y pásalos al modelo
        model.addAttribute("muebles", muebleService.getAllMuebles());
        return "Usuarios/Catalogo";
    }

    @GetMapping("/Usuarios/Details")
    public String mostrarDetallesUsuario() {
        return "Usuarios/details"; // Redirige a la vista "details.html"
    }


    @GetMapping("/Usuarios/PersonalizarM")
    public String personalizarM() {
        return "Usuarios/PersonalizarM";
    }

    @GetMapping("/Usuarios/RealizarP")
    public String RealizarP() {
        return "Usuarios/RealizarP";
    }
}
