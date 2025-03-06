package com.wjcarpi.servicio;

import com.wjcarpi.modelo.Mueble;
import com.wjcarpi.repositorio.MuebleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class MuebleService {

    private final MuebleRepository repository;
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public MuebleService(MuebleRepository repository) {
        this.repository = repository;
    }

    // Obtener todos los muebles
    public List<Mueble> getAllMuebles() {
        return repository.findAll();
    }

    // Guardar un mueble nuevo con imagen
    public Mueble saveMueble(MultipartFile file, Mueble mueble) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path);
            mueble.setImagen(fileName);
        }
        return repository.save(mueble);
    }

    // Obtener un mueble por su ID
    public Optional<Mueble> getMuebleById(Long id) {
        return repository.findById(id);
    }

    // Actualizar un mueble existente
    public Mueble updateMueble(Long id, Mueble updatedMueble, MultipartFile file) throws IOException {
        Optional<Mueble> existingMuebleOpt = repository.findById(id);
        if (existingMuebleOpt.isEmpty()) {
            throw new IllegalArgumentException("Mueble con ID " + id + " no encontrado");
        }

        Mueble existingMueble = existingMuebleOpt.get();
        existingMueble.setNombreMueble(updatedMueble.getNombreMueble());
        existingMueble.setDescripcion(updatedMueble.getDescripcion());
        existingMueble.setPrecio(updatedMueble.getPrecio());
        existingMueble.setStock(updatedMueble.getStock());

        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path);
            existingMueble.setImagen(fileName);
        }

        return repository.save(existingMueble);
    }

    // Eliminar un mueble
    public void deleteMueble(Long id) {
        Optional<Mueble> muebleOpt = repository.findById(id);
        if (muebleOpt.isPresent()) {
            Mueble mueble = muebleOpt.get();
            if (mueble.getImagen() != null) {
                Path path = Paths.get(UPLOAD_DIR + mueble.getImagen());
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    System.err.println("No se pudo eliminar la imagen: " + e.getMessage());
                }
            }
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Mueble con ID " + id + " no encontrado");
        }
    }
}
