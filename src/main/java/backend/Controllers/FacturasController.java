package backend.Controllers;

import backend.DTOS.EditarFacturaDetallesDTO;
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

    /**
     * @return List<Facturas>
     * @description Método para obtener todas las facturas.
     *              Este método devuelve una lista de todas las facturas.
     */
    @GetMapping("facturas")
    public List<Facturas> obtenerFacturasPorIdUsuario(@RequestParam Long idUsuario) {
        return facturasService.obtenerFacturasPorUsuario(idUsuario);
    }

    /**
     * @param id de tipo Long
     * @return Facturas
     * @description Método para obtener una factura por su ID.
     *              Si no se encuentra, devuelve null.
     */
    @GetMapping("factura/{id}")
    public Facturas obtenerFacturasPorID(@PathVariable Long id) {
        return facturasService.buscarFacturaPorId(id);
    }

    /**
     * @param numeroFactura de tipo String
     * @return Facturas
     * @description Método para obtener una factura por su número de factura.
     *              Si no se encuentra, devuelve null.
     */
    @GetMapping("factura/numero/{numeroFactura}")
    public Facturas obtenerFacturasPorID(@PathVariable String numeroFactura) {
        return facturasService.encontrarFacturaPorNumeroFactura(numeroFactura);
    }

    // FacturasController.java
    @GetMapping("facturas/libres")
    public List<Facturas> obtenerFacturasNoAsignadasAGastos(@RequestParam Long idUsuario) {
        return facturasService.obtenerFacturasNoAsignadasAGastos(idUsuario);
    }

    /**
     * @param idUsuario de tipo Long
     * @return List<Facturas>
     * @description Método para obtener todas las facturas de un usuario por su ID.
     *              Si el usuario no existe, devuelve null.
     */
    @PostMapping("nueva_factura")
    public ResponseEntity<?> crearFactura(@RequestBody FacturaDetallesDTO facturaDetallesDTO) {
        return facturasService.crearFactura(facturaDetallesDTO);
    }

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para eliminar una factura por su ID.
     *              Si la factura no existe, no hace nada.
     */
    @PutMapping("editar_factura/{id}")
    public ResponseEntity<?> editarFactura(@PathVariable Long id,
            @RequestBody EditarFacturaDetallesDTO editarFacturaDetallesDTO) {
        return facturasService.actualizarFactura(id, editarFacturaDetallesDTO);
    }

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para eliminar una factura por su ID.
     *              Si la factura no existe, no hace nada.
     */
    @DeleteMapping("borrar_factura/{id}")
    public ResponseEntity<?> borrarFactura(@PathVariable Long id) {
        return facturasService.borrarFactura(id);
    }
}
