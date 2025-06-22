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

    /**
     * @return List<Proveedores>
     * @description Método para obtener todos los proveedores.
     *              Este método devuelve una lista de todos los proveedores.
     */
    @GetMapping("proveedores")
    private List<Proveedores> obtenerTodos() {
        return proveedoresService.obtenerTodos();
    }

    /**
     * @param id de tipo Long
     * @return Proveedores
     * @description Método para obtener un proveedor por su ID.
     *              Si no se encuentra, devuelve null.
     */
    @GetMapping("proveedor/{id}")
    private Proveedores obtenerPorId(@PathVariable Long id) {
        return proveedoresService.obtenerPorId(id);
    }

    /**
     * @param idUsuario de tipo Long
     * @return List<Proveedores>
     * @description Método para obtener todos los proveedores de un usuario por su
     *              ID.
     *              Si el usuario no existe, devuelve null.
     */
    @PostMapping("crear_proveedor")
    private ResponseEntity<?> crearProveedor(@RequestBody Proveedores proveedor) {
        return proveedoresService.crearProveedor(proveedor);
    }

    /**
     * @param proveedor de tipo Proveedores
     * @param id        de tipo Long
     * @return ResponseEntity<?>
     * @description Método para actualizar un proveedor.
     *              Si el proveedor no existe, devuelve un mensaje de error.
     */
    @PutMapping("editar_proveedor/{id}")
    private ResponseEntity<?> editarProveedor(@RequestBody Proveedores proveedor, @PathVariable Long id) {
        return proveedoresService.actualizarProveedor(proveedor, id);
    }

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para eliminar un proveedor por su ID.
     *              Si el proveedor no existe, devuelve un mensaje de error.
     */
    @DeleteMapping("borrar_proveedor/{id}")
    private ResponseEntity<?> borrarProveedor(@PathVariable Long id) {
        return proveedoresService.borrarProveedor(id);
    }
}
