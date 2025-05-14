package backend.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ClientesTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        Clientes cliente = new Clientes();
        cliente.setId(1L);
        cliente.setNombre_cliente("Empresa S.A.");
        cliente.setCif_cliente("A12345678");
        cliente.setDireccion_cliente("Calle Falsa 123");
        cliente.setEmail_cliente("contacto@empresa.com");
        cliente.setTelefono_cliente("123456789");

        assertEquals(1L, cliente.getId());
        assertEquals("Empresa S.A.", cliente.getNombre_cliente());
        assertEquals("A12345678", cliente.getCif_cliente());
        assertEquals("Calle Falsa 123", cliente.getDireccion_cliente());
        assertEquals("contacto@empresa.com", cliente.getEmail_cliente());
        assertEquals("123456789", cliente.getTelefono_cliente());
        assertNotNull(cliente.getFacturas());
        assertTrue(cliente.getFacturas().isEmpty());
    }

    @Test
    void testAllArgsConstructor() {
        Facturas factura = new Facturas();
        List<Facturas> facturasList = List.of(factura);

        Clientes cliente = new Clientes(
                2L,
                "Cliente B",
                "B87654321",
                "Avenida Siempre Viva 742",
                "cliente@b.com",
                "987654321",
                facturasList);

        assertEquals(2L, cliente.getId());
        assertEquals("Cliente B", cliente.getNombre_cliente());
        assertEquals("B87654321", cliente.getCif_cliente());
        assertEquals("Avenida Siempre Viva 742", cliente.getDireccion_cliente());
        assertEquals("cliente@b.com", cliente.getEmail_cliente());
        assertEquals("987654321", cliente.getTelefono_cliente());
        assertEquals(facturasList, cliente.getFacturas());
    }

    @Test
    void testFacturasListModification() {
        Clientes cliente = new Clientes();
        Facturas factura = new Facturas();
        cliente.getFacturas().add(factura);
        assertEquals(1, cliente.getFacturas().size());
        cliente.getFacturas().remove(factura);
        assertTrue(cliente.getFacturas().isEmpty());
    }
}