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

    @GetMapping("gastos")
    private List<Gastos> obtenerTodos() {
        return gastosService.obtenerTodos();
    }

    @GetMapping("gasto/{id}")
    private Gastos obtenerPorId(@PathVariable Long id) {
        return gastosService.obtenerPorId(id);
    }

    @PostMapping("crear_gasto")
    private ResponseEntity<?> crearGasto(@RequestBody GastosDTO gastosDTO) {
        return gastosService.crearGasto(gastosDTO);
    }

    @PutMapping("actualizar_gasto/{id}")
    private ResponseEntity<?> actualizarGasto(@RequestBody GastosDTO gastosDTO, @PathVariable Long id) {
        return gastosService.actualizarGasto(gastosDTO, id);
    }

    @DeleteMapping("borrar_gasto/{id}")
    private ResponseEntity<?> crearGasto(@PathVariable Long id) {
        return gastosService.borrarGasto(id);
    }
}
