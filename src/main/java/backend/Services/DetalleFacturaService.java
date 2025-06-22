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

    /**
     * @param detalles de tipo List<DetalleFactura>
     * @param factura  de tipo Facturas
     * @return ResponseEntity<?>
     * @description Método para crear los detalles de una factura.
     *              Asocia cada detalle a la factura correspondiente.
     */
    public ResponseEntity<?> crearDetalleFactura(List<DetalleFactura> detalles, Facturas factura) {

        // factura.getFacturasDetalles().clear();
        for (DetalleFactura detalle : detalles) {
            System.out.println(detalle.getDescripcion());
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
