package backend.Entity;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FacturasTest {

    private Facturas factura;

    @BeforeEach
    void setUp() {
        factura = new Facturas();
        factura.setId(1L);
        factura.setNumero_factura("F-001");
        factura.setFecha_emision(new Date());
        factura.setSubtotal(new BigDecimal("100.00"));
        factura.setIva(new BigDecimal("12.00"));
        factura.setTotal(new BigDecimal("112.00"));
        factura.setEstado(Estado.Pendiente);

        DetalleFactura detalle = new DetalleFactura();
        detalle.setFactura(factura);
        factura.setFacturasDetalles(Arrays.asList(detalle));

        Usuarios usuario = new Usuarios();
        factura.setUsuario(usuario);

        DatosEmpresa empresa = new DatosEmpresa();
        factura.setEmpresa(empresa);

        Clientes cliente = new Clientes();
        factura.setCliente(cliente);
    }

    @Test
    void testFacturaFields() {
        assertEquals(1L, factura.getId());
        assertEquals("F-001", factura.getNumero_factura());
        assertNotNull(factura.getFecha_emision());
        assertEquals(new BigDecimal("100.00"), factura.getSubtotal());
        assertEquals(new BigDecimal("12.00"), factura.getIva());
        assertEquals(new BigDecimal("112.00"), factura.getTotal());
        assertEquals(Estado.Pendiente, factura.getEstado());
    }

    @Test
    void testFacturasDetalles() {
        assertNotNull(factura.getFacturasDetalles());
        assertEquals(1, factura.getFacturasDetalles().size());
        assertEquals(factura, factura.getFacturasDetalles().get(0).getFactura());
    }

    @Test
    void testUsuarioRelation() {
        assertNotNull(factura.getUsuario());
    }

    @Test
    void testEmpresaRelation() {
        assertNotNull(factura.getEmpresa());
    }

    @Test
    void testClienteRelation() {
        assertNotNull(factura.getCliente());
    }

    @Test
    void testSettersAndGetters() {
        Facturas f = new Facturas();
        f.setId(2L);
        f.setNumero_factura("F-002");
        f.setFecha_emision(new Date());
        f.setSubtotal(BigDecimal.ZERO);
        f.setIva(BigDecimal.ZERO);
        f.setTotal(BigDecimal.ZERO);
        f.setEstado(Estado.Pagada);

        assertEquals(2L, f.getId());
        assertEquals("F-002", f.getNumero_factura());
        assertEquals(BigDecimal.ZERO, f.getSubtotal());
        assertEquals(BigDecimal.ZERO, f.getIva());
        assertEquals(BigDecimal.ZERO, f.getTotal());
        assertEquals(Estado.Pagada, f.getEstado());
    }
}