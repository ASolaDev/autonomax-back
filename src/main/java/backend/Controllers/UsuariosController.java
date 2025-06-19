package backend.Controllers;

import backend.Entity.Usuarios;
import backend.LoginRequest;
import backend.Services.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autonomax/")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")

public class UsuariosController {
    @Autowired
    private UsuariosService usuariosService;

    @GetMapping("usuarios")
    @Operation(summary = "Devolver todos los usuarios", description = "Devuelve todos los usuarios")
    public List<Usuarios> ObtenerTodosLosUsuarios() {
        return usuariosService.obtenerTodosLosUsuarios();
    }

    @GetMapping("usuario/{id}")
    @Operation(summary = "Obtener un usuario", description = "Dado un id, devuelve un el usuario, en caso contrario no devuelve nada")
    public Usuarios obtenerUsuarioPorID(@PathVariable Long id) {
        return usuariosService.obtenerUsuarioPorID(id);
    }

    @PostMapping("nuevo_usuario")
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario validando el email, el nombre y la contraseña. Devuelve el usuario creado si todo es correcto.")
    public ResponseEntity<?> crearNuevoUsuario(@RequestBody Usuarios usuario) {
        return usuariosService.crearUsuario(usuario);
    }

    @PostMapping("login")
    @Operation(summary = "Loguear un usuario", description = "A partir de un email y una contraseña devuelve una respuesta afirmativa o negativa")
    public ResponseEntity<?> Login(@RequestBody LoginRequest loginRequest, HttpSession httpSession) {
        return usuariosService.Login(loginRequest.getNombreUsuario(), loginRequest.getPassword(), httpSession);
    }

    @PutMapping("usuario/{id}")
    @Operation(summary = "Actualizar un usuario", description = "Actualiza al usuario mediante un id y un objeto Usuario (obligatorio esto para actualizar)")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuarios usuario) {
        return usuariosService.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("usuario/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina al usuario de la base de datos, si no lo encuentra no hace nada")
    public void BorrarUsuarios(@PathVariable Long id) {
        usuariosService.eliminarUsuario(id);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().body("Sesión cerrada");
    }

}
