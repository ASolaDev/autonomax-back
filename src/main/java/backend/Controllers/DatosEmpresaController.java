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

    /**
     * @return DatosEmpresa
     * @description Método para obtener los datos de la empresa.
     *              Este método devuelve los datos de la empresa.
     */
    @GetMapping("empresa/{id}")
    private DatosEmpresa obtenerPorId(@PathVariable Long id) {
        return datosEmpresaService.obtenerPorId(id);
    }
}
