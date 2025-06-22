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

    /**
     * @description Método para obtener todos los usuarios.
     *              Este método devuelve una lista de todos los usuarios.
     * @return List<Usuarios> Lista de usuarios.
     */
    public List<Usuarios> obtenerTodosLosUsuarios() {
        return this.usuariosRepository.findAll();
    }

    /**
     * @param id de tipo Long
     * @return Usuarios
     * @description Método para obtener un usuario por su ID.
     *              Si no se encuentra, devuelve null.
     */
    public Usuarios obtenerUsuarioPorID(Long id) {
        return this.usuariosRepository.findById(id).orElse(null);
    }

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para eliminar un usuario por su ID.
     *              Si el usuario no existe, devuelve un mensaje de error.
     */
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

    /**
     * @param usuario de tipo Usuarios
     * @return ResponseEntity<?>
     * @description Método para crear un nuevo usuario.
     *              Si el usuario ya existe, devuelve un mensaje de error.
     */
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

    /**
     * @param id de tipo Long
     * @description Método para eliminar un usuario por su ID.
     *              Si el usuario no existe, lanza una excepción.
     */
    public void eliminarUsuario(Long id) {
        Usuarios usuario_encontrado = obtenerUsuarioPorID(id);

        if (usuario_encontrado != null) {
            usuariosRepository.delete(usuario_encontrado);
        } else {
            throw new EntityNotFoundException("Usuario no encontrado para eliminar");
        }
    }

    /**
     * @param nombreUsuario de tipo String
     * @param password      de tipo String
     * @param httpSession   de tipo HttpSession
     * @return ResponseEntity<?>
     * @description Método para iniciar sesión.
     *              Comprueba si el usuario existe y si la contraseña es correcta.
     */
    public ResponseEntity<?> Login(String nombreUsuario, String password, HttpSession httpSession) {

        Usuarios usuEncontrado = usuariosRepository.ComprobarUsuarioPorNombreUsuario(nombreUsuario);

        if (usuEncontrado != null) {
            // Comprobamos la contraseña hasheada con plana (la que mete el usuario)
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (encoder.matches(password.trim(), usuEncontrado.getPassword())) {
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

    /**
     * @param email de tipo String
     * @return boolean
     * @description Método para validar el formato de un email.
     *              Utiliza una expresión regular para comprobar si el email es
     *              válido.
     */
    private boolean esEmailValido(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(EMAIL_REGEX);
    }

    /**
     * @param email          de tipo String
     * @param usuario_editar de tipo boolean
     * @param id             de tipo Long
     * @return boolean
     * @description Método para comprobar si un email ya está registrado.
     *              Si el usuario está editando, comprueba que el email no
     *              pertenezca a otro usuario.
     */
    private boolean comprobarEmail(String email, boolean usuario_editar, Long id) {

        if (usuario_editar) {
            Usuarios usuarioExistente = usuariosRepository.ComprobarUsuarioPorEmail(email);
            return (usuarioExistente != null && !usuarioExistente.getId().equals(id));
        }

        return usuariosRepository.ComprobarUsuarioPorEmail(email) != null;
    }

    /**
     * @param nombreUsuario  de tipo String
     * @param usuario_editar de tipo boolean
     * @param id             de tipo Long
     * @return boolean
     * @description Método para comprobar si un nombre de usuario ya está
     *              registrado.
     *              Si el usuario está editando, comprueba que el nombre de usuario
     *              no pertenezca a otro usuario.
     */
    private boolean comprobarNombreUsuario(String nombreUsuario, boolean usuario_editar, Long id) {

        if (usuario_editar) {
            Usuarios usuarioExistente = usuariosRepository.ComprobarUsuarioPorNombreUsuario(nombreUsuario);
            return (usuarioExistente != null && !usuarioExistente.getId().equals(id));
        }

        return usuariosRepository.ComprobarUsuarioPorNombreUsuario(nombreUsuario) != null;
    }

    /**
     * @param usuario        de tipo Usuarios
     * @param usuario_editar de tipo boolean
     * @param id             de tipo Long
     * @return ResponseEntity<?>
     * @description Método para validar un usuario.
     *              Comprueba que el nombre, la contraseña y el email cumplan con
     *              los requisitos.
     */
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

    /**
     * @param clave de tipo String
     * @return String
     * @description Método para hashear una contraseña utilizando BCrypt.
     *              Si la contraseña está vacía, devuelve la contraseña sin cambios.
     */
    public String hashearPass(String clave) {

        if (!clave.isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(clave);
        } else {
            return clave;
        }
    }

}
