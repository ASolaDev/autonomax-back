package backend.Services;

import backend.Entity.DetalleFactura;
import backend.Entity.Facturas;
import backend.Repository.DetalleFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleFacturaService {

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    public ResponseEntity<?> crearDetalleFactura(List<DetalleFactura> detalles, Facturas factura) {

        // Borramos todos los detalles que tenía esa factura, ya que en front decide el usuario todos los detalles
        factura.getFacturasDetalles().clear();

        for (DetalleFactura detalle : detalles) {
            detalle.setFactura(factura);
            detalle.setDescripcion(detalle.getDescripcion());
            detalle.setPrecioUnitario(detalle.getPrecioUnitario());
            detalle.setCantidad(detalle.getCantidad());
            detalle.setSubtotal(detalle.getSubtotal());
            detalleFacturaRepository.save(detalle);
        }

        return null;
    }
}
