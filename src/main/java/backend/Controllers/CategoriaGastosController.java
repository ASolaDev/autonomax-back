package backend.Controllers;

import backend.Entity.CategoriaGastos;
import backend.Services.CategoriaGastosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autonomax/")
public class CategoriaGastosController {

    @Autowired
    private CategoriaGastosService categoriaGastosService;

    /**
     * @return List<CategoriaGastos>
     * @description Método para obtener todas las categorías de gastos.
     */
    @GetMapping("categorias")
    private List<CategoriaGastos> obtenerTodos() {
        return this.categoriaGastosService.obtenerTodos();
    }

    /**
     * @param id de tipo Long
     * @return CategoriaGastos
     * @description Método para obtener una categoría de gastos por su ID.
     *              Si no se encuentra, devuelve null.
     */
    @GetMapping("categoria/{id}")
    private CategoriaGastos obtenerID(@PathVariable Long id) {
        return this.categoriaGastosService.obtenerCategoria(id);
    }

    /**
     * @param categoriaGastos de tipo CategoriaGastos
     * @return ResponseEntity<?>
     * @description Método para crear una nueva categoría de gastos.
     *              Si la categoría ya existe, devuelve un mensaje de error.
     */
    @PostMapping("nueva_categoria")
    private ResponseEntity<?> crearNueva(@RequestBody CategoriaGastos categoriaGastos) {
        return this.categoriaGastosService.crearCategoria(categoriaGastos);
    }

    /**
     * @param id              de tipo Long
     * @param categoriaGastos de tipo CategoriaGastos
     * @return ResponseEntity<?>
     * @description Método para actualizar una categoría de gastos.
     *              Si la categoría no existe, devuelve un mensaje de error.
     */
    @PutMapping("categoria/{id}")
    private ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaGastos categoriaGastos) {
        return this.categoriaGastosService.actualizarCategoria(id, categoriaGastos);
    }

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para eliminar una categoría de gastos por su ID.
     *              Si la categoría no existe, devuelve un mensaje de error.
     */
    @DeleteMapping("categoria/{id}")
    private ResponseEntity<?> borrarCategoria(@PathVariable Long id) {
        return this.categoriaGastosService.borrarCategoria(id);
    }
}
