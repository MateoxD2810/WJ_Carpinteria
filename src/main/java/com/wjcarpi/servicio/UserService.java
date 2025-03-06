package com.wjcarpi.servicio;

import com.wjcarpi.modelo.User;
import com.wjcarpi.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Mapa temporal para almacenar códigos de recuperación asociados a Gmail
    private final Map<String, String> recoveryCodes = new HashMap<>();

    public void eliminarUsuario(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    //Añadido



    public Optional<User> buscarPorId(Long id) {
        return userRepository.findById(id);
    }
    
    
    //Añadido

    public Optional<User> buscarPorUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> buscarPorGmail(String gmail) {
        return userRepository.findByGmail(gmail);
    }

    private String generateRecoveryCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public String recuperarContrasena(String gmail) {
        Optional<User> usuario = userRepository.findByGmail(gmail);
        if (usuario.isPresent()) {
            String recoveryCode = generateRecoveryCode();
            recoveryCodes.put(gmail, recoveryCode);

            // Enviar código al correo electrónico
            emailService.sendRecoveryCode(gmail, recoveryCode);
            return recoveryCode; // Retorno para pruebas o registro
        } else {
            throw new RuntimeException("Usuario no encontrado con Gmail: " + gmail);
        }
    }

    public boolean verifyRecoveryCode(String code, String newPassword) {
        Optional<String> matchingGmail = recoveryCodes.entrySet().stream()
                .filter(entry -> entry.getValue().equals(code))
                .map(Map.Entry::getKey)
                .findFirst();

        if (matchingGmail.isPresent()) {
            String gmail = matchingGmail.get();
            Optional<User> usuario = userRepository.findByGmail(gmail);
            if (usuario.isPresent()) {
                User user = usuario.get();
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                recoveryCodes.remove(gmail);
                return true;
            }
        }
        return false;
    }

    public User guardarUsuario(User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByGmail(user.getGmail())) {
            throw new RuntimeException("Ya existe un usuario con ese nombre de usuario o Gmail.");
        }
        return userRepository.save(user);
    }

    // Método para actualizar los datos del usuario
    public User actualizarUsuario(User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(updatedUser.getId());

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Verificar que el correo electrónico no esté en uso por otro usuario
            if (!existingUser.getGmail().equals(updatedUser.getGmail()) &&
                userRepository.existsByGmail(updatedUser.getGmail())) {
                throw new RuntimeException("Ya existe un usuario con el mismo Gmail.");
            }

            // Actualizamos los campos del usuario
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setGmail(updatedUser.getGmail());
            existingUser.setTelefono(updatedUser.getTelefono());
            existingUser.setDireccion(updatedUser.getDireccion());

            // Si no se quiere cambiar la contraseña, se mantiene la misma
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + updatedUser.getId());
        }
    }

}
