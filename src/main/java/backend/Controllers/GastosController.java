package backend.Controllers;

import backend.DTOS.GastosDTO;
import backend.Entity.Gastos;
import backend.Services.GastosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autonomax/")
public class GastosController {

    @Autowired
    private GastosService gastosService;

    /**
     * @return List<Gastos>
     * @description Método para obtener todos los gastos de un determinado usuario.
     *              Este método devuelve una lista de todos los gastos de ese usuario.
     */
    @GetMapping("gastos")
    private List<Gastos> obtenerTodosPorIdUsuario(@RequestParam Long idUsuario) {
        return gastosService.obtenerTodosPorIdUsuario(idUsuario);
    }


    /**
     * @param id de tipo Long
     * @return Gastos
     * @description Método para obtener un gasto por su ID.
     *              Si no se encuentra, devuelve null.
     */
    @GetMapping("gasto/{id}")
    private Gastos obtenerPorId(@PathVariable Long id) {
        return gastosService.obtenerPorId(id);
    }

    /**
     * @param idUsuario de tipo Long
     * @return List<Gastos>
     * @description Método para obtener todos los gastos de un usuario por su ID.
     *              Si el usuario no existe, devuelve null.
     */
    @PostMapping("crear_gasto")
    private ResponseEntity<?> crearGasto(@RequestBody GastosDTO gastosDTO) {
        return gastosService.crearGasto(gastosDTO);
    }

    /**
     * @param gastosDTO de tipo GastosDTO
     * @param id        de tipo Long
     * @return ResponseEntity<?>
     * @description Método para actualizar un gasto.
     *              Si el gasto no existe, devuelve un mensaje de error.
     */
    @PutMapping("actualizar_gasto/{id}")
    private ResponseEntity<?> actualizarGasto(@RequestBody GastosDTO gastosDTO, @PathVariable Long id) {
        return gastosService.actualizarGasto(gastosDTO, id);
    }

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para eliminar un gasto por su ID.
     *              Si el gasto no existe, devuelve un mensaje de error.
     */
    @DeleteMapping("borrar_gasto/{id}")
    private ResponseEntity<?> crearGasto(@PathVariable Long id) {
        return gastosService.borrarGasto(id);
    }
}
