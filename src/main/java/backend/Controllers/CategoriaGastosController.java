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

    @GetMapping("categorias")
    private List<CategoriaGastos> obtenerTodos() {
        return this.categoriaGastosService.obtenerTodos();
    }

    @GetMapping("categoria/{id}")
    private CategoriaGastos obtenerID(@PathVariable Long id) {
        return this.categoriaGastosService.obtenerCategoria(id);
    }

    @PostMapping("nueva_categoria")
    private ResponseEntity<?> crearNueva(@RequestBody CategoriaGastos categoriaGastos) {
        return this.categoriaGastosService.crearCategoria(categoriaGastos);
    }

    @PutMapping("categoria/{id}")
    private ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaGastos categoriaGastos) {
        return this.categoriaGastosService.actualizarCategoria(id, categoriaGastos);
    }

    @DeleteMapping("categoria/{id}")
    private ResponseEntity<?> borrarCategoria(@PathVariable Long id) {
        return this.categoriaGastosService.borrarCategoria(id);
    }
}
