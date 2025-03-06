package com.wjcarpi.controlador;

import com.wjcarpi.modelo.Mueble;
import com.wjcarpi.servicio.MuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Administrador")
public class MuebleController {

    @Autowired
    private MuebleService muebleService;

    // Mostrar lista de muebles en el catálogo de productos
    @GetMapping("/CatalogoProductos")
    public String catalogoProductos(Model model) {
        List<Mueble> muebles = muebleService.getAllMuebles();
        model.addAttribute("muebles", muebles);
        return "Administrador/CatalogoProductos"; // Vista para el catálogo de productos
    }

    // Mostrar formulario para crear un nuevo producto (mueble)
    @GetMapping("/NuevoProducto")
    public String nuevoProducto(Model model) {
        model.addAttribute("mueble", new Mueble());
        return "Administrador/NuevoProducto"; // Vista para crear un nuevo producto
    }

    // Guardar un nuevo mueble
    @PostMapping("/NuevoProducto")
    public String guardarNuevoProducto(@RequestParam("file") MultipartFile file, @ModelAttribute Mueble mueble) {
        try {
            if (!file.isEmpty()) {
                // Guardar la imagen en el servidor
                String imageName = file.getOriginalFilename();
                mueble.setImagen(imageName); // Establecer el nombre del archivo de imagen en el mueble
            }
            muebleService.saveMueble(file, mueble); // Llamar al servicio para guardar el mueble
        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // Página de error si algo falla
        }
        return "redirect:/Administrador/CatalogoProductos"; // Redirige al catálogo de productos después de guardar
    }

    // Mostrar formulario para editar un mueble
    @GetMapping("/EditarProducto/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Optional<Mueble> muebleOpt = muebleService.getMuebleById(id);
        if (muebleOpt.isPresent()) {
            model.addAttribute("mueble", muebleOpt.get());
            return "Administrador/EditarProducto"; // Vista para editar un mueble
        } else {
            return "redirect:/Administrador/CatalogoProductos"; // Redirige al catálogo si no se encuentra el mueble
        }
    }

    // Actualizar un mueble
    @PostMapping("/EditarProducto/{id}")
    public String actualizarProducto(@PathVariable Long id, @RequestParam("file") MultipartFile file, @ModelAttribute Mueble mueble) {
        try {
            if (!file.isEmpty()) {
                // Si se proporciona una nueva imagen, actualizamos el nombre de la imagen
                String imageName = file.getOriginalFilename();
                mueble.setImagen(imageName); // Establecer el nombre de la imagen
            }
            muebleService.updateMueble(id, mueble, file); // Llamar al servicio para actualizar el mueble
        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // Página de error si algo falla
        }
        return "redirect:/Administrador/CatalogoProductos"; // Redirige al catálogo después de actualizar
    }

    // Eliminar un mueble
    @GetMapping("/EliminarProducto/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        muebleService.deleteMueble(id);
        return "redirect:/Administrador/CatalogoProductos"; // Redirige al catálogo después de eliminar
    }

    // Mostrar detalles de un mueble
    @GetMapping("/DetalleProducto/{id}")
    public String mostrarDetalleProducto(@PathVariable Long id, Model model) {
        Optional<Mueble> muebleOpt = muebleService.getMuebleById(id);
        if (muebleOpt.isPresent()) {
            model.addAttribute("mueble", muebleOpt.get());
            return "Administrador/DetalleProducto"; // Vista para mostrar detalles del mueble
        } else {
            return "redirect:/Administrador/CatalogoProductos"; // Redirige al catálogo si no se encuentra el mueble
        }
    }
}
