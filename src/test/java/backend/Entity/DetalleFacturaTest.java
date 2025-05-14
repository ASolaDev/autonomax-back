package backend.Entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class DetalleFacturaTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        DetalleFactura detalle = new DetalleFactura();
        detalle.setId_detalle(1L);
        detalle.setDescripcion("Producto A");
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(new BigDecimal("10.50"));
        detalle.setSubtotal(new BigDecimal("21.00"));
        Facturas factura = new Facturas();
        detalle.setFactura(factura);

        assertEquals(1L, detalle.getId_detalle());
        assertEquals("Producto A", detalle.getDescripcion());
        assertEquals(2, detalle.getCantidad());
        assertEquals(new BigDecimal("10.50"), detalle.getPrecioUnitario());
        assertEquals(new BigDecimal("21.00"), detalle.getSubtotal());
        assertEquals(factura, detalle.getFactura());
    }

    @Test
    void testAllArgsConstructor() {
        Facturas factura = new Facturas();
        DetalleFactura detalle = new DetalleFactura(
                2L,
                "Producto B",
                3,
                new BigDecimal("5.00"),
                new BigDecimal("15.00"),
                factura);

        assertEquals(2L, detalle.getId_detalle());
        assertEquals("Producto B", detalle.getDescripcion());
        assertEquals(3, detalle.getCantidad());
        assertEquals(new BigDecimal("5.00"), detalle.getPrecioUnitario());
        assertEquals(new BigDecimal("15.00"), detalle.getSubtotal());
        assertEquals(factura, detalle.getFactura());
    }

    @Test
    void testEqualsAndHashCode() {
        Facturas factura = new Facturas();
        DetalleFactura d1 = new DetalleFactura(1L, "desc", 1, BigDecimal.ONE, BigDecimal.ONE, factura);
        DetalleFactura d2 = new DetalleFactura(1L, "desc", 1, BigDecimal.ONE, BigDecimal.ONE, factura);

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void testToString() {
        Facturas factura = new Facturas();
        DetalleFactura detalle = new DetalleFactura(1L, "desc", 1, BigDecimal.ONE, BigDecimal.ONE, factura);
        String str = detalle.toString();
        assertTrue(str.contains("id_detalle=1"));
        assertTrue(str.contains("descripcion=desc"));
    }
}