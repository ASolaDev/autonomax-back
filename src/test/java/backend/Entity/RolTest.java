package backend.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RolTest {

    @Test
    void testEnumValues() {
        Rol[] roles = Rol.values();
        assertEquals(3, roles.length);
        assertArrayEquals(new Rol[] { Rol.Administrador, Rol.Usuario, Rol.Desarollo }, roles);
    }

    @Test
    void testValueOf() {
        assertEquals(Rol.Administrador, Rol.valueOf("Administrador"));
        assertEquals(Rol.Usuario, Rol.valueOf("Usuario"));
        assertEquals(Rol.Desarollo, Rol.valueOf("Desarollo"));
    }

    @Test
    void testInvalidValueOfThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Rol.valueOf("Invalido"));
    }
}