package backend.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstadoTest {

    @Test
    void testEnumValues() {
        Estado[] values = Estado.values();
        assertEquals(2, values.length);
        assertEquals(Estado.Pendiente, values[0]);
        assertEquals(Estado.Pagada, values[1]);
    }

    @Test
    void testValueOf() {
        assertEquals(Estado.Pendiente, Estado.valueOf("Pendiente"));
        assertEquals(Estado.Pagada, Estado.valueOf("Pagada"));
    }

    @Test
    void testInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> Estado.valueOf("Invalido"));
    }
}