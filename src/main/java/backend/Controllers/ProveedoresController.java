package backend.Controllers;

import backend.Entity.Proveedores;
import backend.Services.ProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autonomax/")
public class ProveedoresController {

    @Autowired
    private ProveedoresService proveedoresService;

    @GetMapping("proveedores")
    private List<Proveedores> obtenerTodos() {
        return proveedoresService.obtenerTodos();
    }

    @GetMapping("proveedor/{id}")
    private Proveedores obtenerPorId(@PathVariable Long id) {
        return proveedoresService.obtenerPorId(id);
    }

    @PostMapping("crear_proveedor")
    private ResponseEntity<?> crearProveedor(@RequestBody Proveedores proveedor) {
        return proveedoresService.crearProveedor(proveedor);
    }

    @PutMapping("editar_proveedor/{id}")
    private ResponseEntity<?> editarProveedor(@RequestBody Proveedores proveedor, @PathVariable Long id) {
        return proveedoresService.actualizarProveedor(proveedor, id);
    }

    @DeleteMapping("borrar_proveedor/{id}")
    private ResponseEntity<?> borrarProveedor(@PathVariable Long id) {
        return proveedoresService.borrarProveedor(id);
    }
}
