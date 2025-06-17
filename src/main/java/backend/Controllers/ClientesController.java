package backend.Controllers;

import backend.Entity.Clientes;
import backend.Services.ClientesServices;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autonomax/")
public class ClientesController {
    @Autowired
    private ClientesServices clientesService;

    @GetMapping("clientes")
    @Operation(summary = "Devolver todos los clientes", description = "Devuelve todos los clientes")
    public List<Clientes> obtenerTodos() {
        return clientesService.obtenerTodos();
    }

    @GetMapping("cliente/{id}")
    @Operation(summary = "Devolver un cliente por ID", description = "Devuelve un cliente dado un ID")
    public Clientes obtenerPorId(@PathVariable Long id) {
        return clientesService.obtenerPorId(id);
    }

    @PostMapping("nuevo_cliente")
    @Operation(summary = "Crea un cliente", description = "Crea un cliente dado un objeto JSON")
    public ResponseEntity<?> crearCliente(@RequestBody Clientes cliente) {
        return clientesService.crearCliente(cliente);
    }

    @PutMapping("cliente/{id}")
    @Operation(summary = "Actualizar un cliente por ID", description = "Actualiza un cliente dado un ID")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Clientes cliente) {
        return clientesService.actualizarCliente(cliente, id);
    }

    @DeleteMapping("cliente/{id}")
    @Operation(summary = "Borrar un cliente por ID", description = "Borra un cliente dado un ID")
    public void eliminarCliente(@PathVariable Long id) {
        clientesService.eliminarCliente(id);
    }

}
