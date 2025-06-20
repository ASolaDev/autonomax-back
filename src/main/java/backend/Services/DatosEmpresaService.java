package backend.Services;

import backend.Entity.DatosEmpresa;
import backend.Repository.DatosEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatosEmpresaService {
    @Autowired
    private DatosEmpresaRepository datosEmpresaRepository;

    public DatosEmpresa obtenerPorId(Long id) {
        return datosEmpresaRepository.findById(id).orElse(null);
    }
}
