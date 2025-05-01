package backend.Controllers;

import backend.Entity.DatosEmpresa;
import backend.Services.DatosEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autonomax/")
public class DatosEmpresaController {
    @Autowired
    private DatosEmpresaService datosEmpresaService;

    // Método para devolver todos los datos de la empresa, en principio se necesita
    // todo
    @GetMapping("empresa/{id}")
    private DatosEmpresa obtenerPorId(@PathVariable Long id) {
        return datosEmpresaService.obtenerPorId(id);
    }
}
