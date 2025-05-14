package backend.Services;

import backend.Entity.Usuarios;
import backend.Repository.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuariosServiceTest {

    @InjectMocks
    private UsuariosService usuariosService;

    @Mock
    private UsuariosRepository usuariosRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosLosUsuariosTest() {
        Usuarios u1 = new Usuarios();
        Usuarios u2 = new Usuarios();
        when(usuariosRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Usuarios> result = usuariosService.obtenerTodosLosUsuarios();

        assertEquals(2, result.size());
        verify(usuariosRepository).findAll();
    }

    @Test
    void obtenerUsuarioPorIDExisteTest() {
        Usuarios usuario = new Usuarios();
        usuario.setId(1L);
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuarios result = usuariosService.obtenerUsuarioPorID(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void obtenerUsuarioPorIDNoExisteTest() {
        when(usuariosRepository.findById(2L)).thenReturn(Optional.empty());

        Usuarios result = usuariosService.obtenerUsuarioPorID(2L);

        assertNull(result);
    }

    @Test
    void crearUsuarioValidoTest() {
        Usuarios usuario = new Usuarios();
        usuario.setNombre_usuario("test");
        usuario.setPassword("1234");
        usuario.setEmail("test@mail.com");
        // usuario.setRol("USER");

        when(usuariosRepository.ComprobarUsuarioPorEmail(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");
        when(usuariosRepository.save(any(Usuarios.class))).thenReturn(usuario);

        ResponseEntity<?> response = usuariosService.crearUsuario(usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario creado", response.getBody());
    }

    @Test
    void crearUsuarioEmailInvalidoTest() {
        Usuarios usuario = new Usuarios();
        usuario.setNombre_usuario("test");
        usuario.setPassword("1234");
        usuario.setEmail("invalid-email");
        // usuario.setRol("USER");

        ResponseEntity<?> response = usuariosService.crearUsuario(usuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("formato del email"));
    }

    @Test
    void crearUsuarioEmailDuplicadoTest() {
        Usuarios usuario = new Usuarios();
        usuario.setNombre_usuario("test");
        usuario.setPassword("1234");
        usuario.setEmail("test@mail.com");
        // usuario.setRol("USER");

        when(usuariosRepository.ComprobarUsuarioPorEmail(anyString())).thenReturn(new Usuarios());

        ResponseEntity<?> response = usuariosService.crearUsuario(usuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("ya está registrado"));
    }

    @Test
    void actualizarUsuarioEncontradoValidoTest() {
        Usuarios usuario = new Usuarios();
        usuario.setNombre_usuario("nuevo");
        usuario.setPassword("1234");
        usuario.setEmail("nuevo@mail.com");
        // usuario.setRol("ADMIN");

        Usuarios existente = new Usuarios();
        existente.setId(1L);
        existente.setNombre_usuario("viejo");
        existente.setPassword("old");
        existente.setEmail("viejo@mail.com");
        // existente.setRol("USER");

        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuariosRepository.ComprobarUsuarioPorEmail(anyString())).thenReturn(null);
        when(usuariosRepository.save(any(Usuarios.class))).thenReturn(existente);

        ResponseEntity<?> response = usuariosService.actualizarUsuario(1L, usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario actualizado!", response.getBody());
    }

    @Test
    void actualizarUsuarioNoExisteTest() {
        Usuarios usuario = new Usuarios();
        when(usuariosRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = usuariosService.actualizarUsuario(1L, usuario);

        assertNull(response);
    }

    @Test
    void eliminarUsuarioExisteTest() {
        Usuarios usuario = new Usuarios();
        usuario.setId(1L);
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuariosService.eliminarUsuario(1L));
        verify(usuariosRepository).delete(usuario);
    }

    @Test
    void eliminarUsuarioNoExisteTest() {
        when(usuariosRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuariosService.eliminarUsuario(1L));
    }

    @Test
    void loginExitoTest() {
        Usuarios usuario = new Usuarios();
        usuario.setEmail("test@mail.com");
        usuario.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("1234"));

        when(usuariosRepository.ComprobarUsuarioPorEmail("test@mail.com")).thenReturn(usuario);

        ResponseEntity<?> response = usuariosService.Login("test@mail.com", "1234");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void loginPassIncorrectaTest() {
        Usuarios usuario = new Usuarios();
        usuario.setEmail("test@mail.com");
        usuario.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("1234"));

        when(usuariosRepository.ComprobarUsuarioPorEmail("test@mail.com")).thenReturn(usuario);

        ResponseEntity<?> response = usuariosService.Login("test@mail.com", "wrong");

        assertEquals(401, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("Contraseña no coincide"));
    }

    @Test
    void loginUsuarioNoExisteTest() {
        when(usuariosRepository.ComprobarUsuarioPorEmail("notfound@mail.com")).thenReturn(null);

        ResponseEntity<?> response = usuariosService.Login("notfound@mail.com", "1234");

        assertEquals(401, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("Usuario no encontrado"));
    }

    @Test
    void hashearPassNoVaciaTest() {
        String raw = "clave";
        String hashed = usuariosService.hashearPass(raw);

        assertNotNull(hashed);
        assertNotEquals(raw, hashed);
        assertTrue(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().matches(raw, hashed));
    }

    @Test
    void hashearPassVaciaTest() {
        String raw = "";
        String hashed = usuariosService.hashearPass(raw);

        assertEquals("", hashed);
    }
}