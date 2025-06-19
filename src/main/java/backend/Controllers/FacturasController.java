package backend.Controllers;

import backend.DTOS.FacturaDetallesDTO;
import backend.Entity.Facturas;
import backend.Services.FacturasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autonomax/")
public class FacturasController {

    @Autowired
    private FacturasService facturasService;

    @GetMapping("facturas")
    public List<Facturas> obtenerFacturasPorIdUsuario(@RequestParam Long idUsuario) {
        return facturasService.obtenerFacturasPorUsuario(idUsuario);
    }

    @GetMapping("factura/{id}")
    public Facturas obtenerFacturasPorID(@PathVariable Long id) {
        return facturasService.buscarFacturaPorId(id);
    }

    @GetMapping("factura/numero/{numeroFactura}")
    public Facturas obtenerFacturasPorID(@PathVariable String numeroFactura) {
        return facturasService.encontrarFacturaPorNumeroFactura(numeroFactura);
    }

    @PostMapping("nueva_factura")
    public ResponseEntity<?> crearFactura(@RequestBody FacturaDetallesDTO facturaDetallesDTO) {
        return facturasService.crearFactura(facturaDetallesDTO);
    }

    @PutMapping("editar_factura/{id}")
    public ResponseEntity<?> editarFactura(@PathVariable Long id, @RequestBody FacturaDetallesDTO facturaDetallesDTO) {
        return facturasService.actualizarFactura(id, facturaDetallesDTO);
    }

    @DeleteMapping("borrar_factura/{id}")
    public ResponseEntity<?> borrarFactura(@PathVariable Long id) {
        return facturasService.borrarFactura(id);
    }
}
