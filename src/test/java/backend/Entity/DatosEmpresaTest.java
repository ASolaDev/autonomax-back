package backend.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

class DatosEmpresaTest {

    @Test
    void testGettersAndSetters() {
        DatosEmpresa empresa = new DatosEmpresa();
        empresa.setId(1L);
        empresa.setNombre_empresa("Empresa S.A.");
        empresa.setCif_empresa("A12345678");
        empresa.setDireccion_empresa("Calle Falsa 123");
        empresa.setEmail_empresa("info@empresa.com");
        empresa.setTelefono_empresa("123456789");

        assertEquals(1L, empresa.getId());
        assertEquals("Empresa S.A.", empresa.getNombre_empresa());
        assertEquals("A12345678", empresa.getCif_empresa());
        assertEquals("Calle Falsa 123", empresa.getDireccion_empresa());
        assertEquals("info@empresa.com", empresa.getEmail_empresa());
        assertEquals("123456789", empresa.getTelefono_empresa());
    }

    @Test
    void testAllArgsConstructor() {
        List<Facturas> facturas = new ArrayList<>();
        DatosEmpresa empresa = new DatosEmpresa(
                2L,
                "Otra Empresa",
                "B87654321",
                "Avenida Siempre Viva 742",
                "contacto@otraempresa.com",
                "987654321",
                facturas);

        assertEquals(2L, empresa.getId());
        assertEquals("Otra Empresa", empresa.getNombre_empresa());
        assertEquals("B87654321", empresa.getCif_empresa());
        assertEquals("Avenida Siempre Viva 742", empresa.getDireccion_empresa());
        assertEquals("contacto@otraempresa.com", empresa.getEmail_empresa());
        assertEquals("987654321", empresa.getTelefono_empresa());
        assertSame(facturas, empresa.getFacturas());
    }

    @Test
    void testNoArgsConstructor() {
        DatosEmpresa empresa = new DatosEmpresa();
        assertNotNull(empresa);
        assertNull(empresa.getId());
        assertNull(empresa.getNombre_empresa());
        assertNull(empresa.getCif_empresa());
        assertNull(empresa.getDireccion_empresa());
        assertNull(empresa.getEmail_empresa());
        assertNull(empresa.getTelefono_empresa());
        assertNotNull(empresa.getFacturas());
        assertTrue(empresa.getFacturas().isEmpty());
    }

    @Test
    void testEqualsAndHashCode() {
        DatosEmpresa empresa1 = new DatosEmpresa();
        empresa1.setId(1L);
        empresa1.setNombre_empresa("Empresa S.A.");

        DatosEmpresa empresa2 = new DatosEmpresa();
        empresa2.setId(1L);
        empresa2.setNombre_empresa("Empresa S.A.");

        assertEquals(empresa1, empresa2);
        assertEquals(empresa1.hashCode(), empresa2.hashCode());
    }

    @Test
    void testToString() {
        DatosEmpresa empresa = new DatosEmpresa();
        empresa.setId(1L);
        empresa.setNombre_empresa("Empresa S.A.");
        String toString = empresa.toString();
        assertTrue(toString.contains("Empresa S.A."));
        assertTrue(toString.contains("id=1"));
    }
}