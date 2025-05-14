package backend.Services;

import backend.Entity.DetalleFactura;
import backend.Entity.Facturas;
import backend.Repository.DetalleFacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DetalleFacturaServiceTest {

    private DetalleFacturaRepository detalleFacturaRepository;
    private DetalleFacturaService detalleFacturaService;

    @BeforeEach
    void setUp() {
        detalleFacturaRepository = mock(DetalleFacturaRepository.class);
        detalleFacturaService = new DetalleFacturaService();
        // Inject mock repository
        var field = DetalleFacturaService.class.getDeclaredFields()[0];
        field.setAccessible(true);
        try {
            field.set(detalleFacturaService, detalleFacturaRepository);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCrearDetalleFactura_SavesAllDetallesWithFactura() {
        Facturas factura = new Facturas();
        factura.setId(1L);

        DetalleFactura detalle1 = new DetalleFactura();
        detalle1.setDescripcion("Producto 1");
        detalle1.setPrecioUnitario(BigDecimal.valueOf(10));
        detalle1.setCantidad(2);
        detalle1.setSubtotal(BigDecimal.valueOf(20));

        DetalleFactura detalle2 = new DetalleFactura();
        detalle2.setDescripcion("Producto 2");
        detalle2.setPrecioUnitario(BigDecimal.valueOf(5));
        detalle2.setCantidad(1);
        detalle2.setSubtotal(BigDecimal.valueOf(5));

        List<DetalleFactura> detalles = Arrays.asList(detalle1, detalle2);

        ResponseEntity<?> response = detalleFacturaService.crearDetalleFactura(detalles, factura);

        // Verify save called for each detalle
        verify(detalleFacturaRepository, times(2)).save(any(DetalleFactura.class));

        // Capture saved detalles
        ArgumentCaptor<DetalleFactura> captor = ArgumentCaptor.forClass(DetalleFactura.class);
        verify(detalleFacturaRepository, times(2)).save(captor.capture());
        List<DetalleFactura> savedDetalles = captor.getAllValues();

        for (DetalleFactura savedDetalle : savedDetalles) {
            assertEquals(factura, savedDetalle.getFactura());
        }

        // The method returns null
        assertNull(response);
    }

    @Test
    void testCrearDetalleFactura_EmptyList() {
        Facturas factura = new Facturas();
        List<DetalleFactura> detalles = List.of();

        ResponseEntity<?> response = detalleFacturaService.crearDetalleFactura(detalles, factura);

        verify(detalleFacturaRepository, never()).save(any());
        assertNull(response);
    }
}