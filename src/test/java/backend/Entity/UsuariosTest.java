package backend.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class UsuariosTest {

    @Test
    void testDefaultConstructorAndSetters() {
        Usuarios usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setNombre_usuario("anton");
        usuario.setEmail("anton@email.com");
        usuario.setPassword("password123");
        usuario.setRol(Rol.Administrador);

        assertEquals(1L, usuario.getId());
        assertEquals("anton", usuario.getNombre_usuario());
        assertEquals("anton@email.com", usuario.getEmail());
        assertEquals("password123", usuario.getPassword());
        assertEquals(Rol.Administrador, usuario.getRol());
    }

    @Test
    void testAllArgsConstructor() {
        Facturas factura = new Facturas();
        List<Facturas> facturasList = List.of(factura);

        Usuarios usuario = new Usuarios(
                2L,
                "maria",
                "maria@email.com",
                "pass456",
                Rol.Usuario,
                facturasList);

        assertEquals(2L, usuario.getId());
        assertEquals("maria", usuario.getNombre_usuario());
        assertEquals("maria@email.com", usuario.getEmail());
        assertEquals("pass456", usuario.getPassword());
        assertEquals(Rol.Usuario, usuario.getRol());
        assertEquals(facturasList, usuario.getFacturas());
    }

    @Test
    void testDefaultRolIsUsuario() {
        Usuarios usuario = new Usuarios();
        assertEquals(Rol.Usuario, usuario.getRol());
    }

    @Test
    void testFacturasListInitialization() {
        Usuarios usuario = new Usuarios();
        assertNotNull(usuario.getFacturas());
        assertTrue(usuario.getFacturas().isEmpty());
    }
}