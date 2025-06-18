package backend.Services;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import backend.Entity.Usuarios;
import backend.Repository.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuariosService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    // Obtener todos los usuarios
    public List<Usuarios> obtenerTodosLosUsuarios() {
        return this.usuariosRepository.findAll();
    }

    // Obtener un usuario por su ID
    public Usuarios obtenerUsuarioPorID(Long id) {
        return this.usuariosRepository.findById(id).orElse(null);
    }

    // Actualizar un usuario
    public ResponseEntity<?> actualizarUsuario(Long id, Usuarios usuario) {
        Usuarios usuEncontrado = obtenerUsuarioPorID(id);

        if (usuEncontrado != null) {
            ResponseEntity<?> validacionResultado = validarUsuario(usuario, true, id);

            if (validacionResultado != null) {
                return validacionResultado;
            }

            usuEncontrado.setNombreUsuario(usuario.getNombreUsuario());
            usuEncontrado.setEmail(usuario.getEmail());
            usuEncontrado.setPassword(hashearPass(usuario.getPassword()));
            usuEncontrado.setRol(usuario.getRol());

            Usuarios usuarioGuardado = usuariosRepository.save(usuEncontrado);

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
        } else {
            return null;
        }
    }

    // Crear un nuevo usuario
    public ResponseEntity<?> crearUsuario(Usuarios usuario) {
        // Para posteriores mejoras, creamos validaciones de nombre y contraseñas con
        // REGEX

        ResponseEntity<?> validacionResultado = validarUsuario(usuario, false, null);
        if (validacionResultado != null) {
            return validacionResultado;
        }

        usuario.setPassword(hashearPass(usuario.getPassword()));

        Usuarios usuarioGuardado = usuariosRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
    }

    // Borrar un usuario
    public void eliminarUsuario(Long id) {
        Usuarios usuario_encontrado = obtenerUsuarioPorID(id);

        if (usuario_encontrado != null) {
            usuariosRepository.delete(usuario_encontrado);
        } else {
            throw new EntityNotFoundException("Usuario no encontrado para eliminar");
        }
    }

    public ResponseEntity<?> Login(String nombreUsuario, String password, HttpSession httpSession) {
        Usuarios usuEncontrado = usuariosRepository.ComprobarUsuarioPorNombreUsuario(nombreUsuario);

        System.out.println("Nombre usuario:" + nombreUsuario + "Contraseña:" + password);

        if (usuEncontrado != null) {
            // Comprobamos la contraseña hasheada con plana (la que mete el usuario)
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (encoder.matches(password.trim(), usuEncontrado.getPassword())) {

                // Guardamos la sesión en back (HTTPSESSION), en consecuencia
                // se genera automáticamente una cookie (JSESSIONID)
                httpSession.setAttribute("idUsuario", usuEncontrado.getId());
                httpSession.setAttribute("emailUsuario", usuEncontrado.getEmail());
                httpSession.setAttribute("nombreUsuario", usuEncontrado.getNombreUsuario());
                httpSession.setAttribute("rolUsuario", usuEncontrado.getRol());
                return new ResponseEntity<>(usuEncontrado, HttpStatus.OK);
            } else
                return ResponseEntity.status(401).body("Contraseña incorrecta.");
        } else {
            return ResponseEntity.status(401).body("Nombre de usuario incorrecto.");
        }
    }

    // Método para validar el formato del email
    private boolean esEmailValido(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(EMAIL_REGEX);
    }

    // Método para comprobar si el email ya está registrado
    private boolean comprobarEmail(String email, boolean usuario_editar, Long id) {
        if (usuario_editar) {
            Usuarios usuarioExistente = usuariosRepository.ComprobarUsuarioPorEmail(email);
            return (usuarioExistente != null && !usuarioExistente.getId().equals(id));
        }

        return usuariosRepository.ComprobarUsuarioPorEmail(email) != null;
    }

    // Método para comprobar si el nombre de usuario ya está registrado
    private boolean comprobarNombreUsuario(String nombreUsuario, boolean usuario_editar, Long id) {
        if (usuario_editar) {
            Usuarios usuarioExistente = usuariosRepository.ComprobarUsuarioPorNombreUsuario(nombreUsuario);
            return (usuarioExistente != null && !usuarioExistente.getId().equals(id));
        }

        return usuariosRepository.ComprobarUsuarioPorNombreUsuario(nombreUsuario) != null;
    }

    // Metodo para validar al Usuario (sea si se ha encontrado o no, para no repetir
    // código en put/post
    private ResponseEntity<?> validarUsuario(Usuarios usuario, boolean usuario_editar, Long id) {
        if (usuario.getNombreUsuario().trim().length() < 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El nombre no cumple con la longitud suficiente (minimo 3)");
        }

        if (usuario.getPassword().trim().length() < 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La contraseña no cumple con la longitud suficiente (minimo 3)");
        }

        if (!esEmailValido(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El formato del email no es válido.");
        }

        if (comprobarEmail(usuario.getEmail(), usuario_editar, id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El email ya está registrado.");
        }

        if (comprobarNombreUsuario(usuario.getNombreUsuario(), usuario_editar, id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El nombre de usuario ya está registrado.");
        }

        return null;
    }

    // Metodo para hashear la contraseña en BD
    public String hashearPass(String clave) {
        if (!clave.isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(clave);
        } else {
            return clave;
        }
    }

}
