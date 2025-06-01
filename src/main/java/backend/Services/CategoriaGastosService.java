package backend.Services;

import backend.Entity.CategoriaGastos;
import backend.Repository.CategoriaGastosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaGastosService {

    @Autowired
    private CategoriaGastosRepository categoriaGastosRepository;

    public List<CategoriaGastos> obtenerTodos() {
        return categoriaGastosRepository.findAll();
    }

    public CategoriaGastos obtenerCategoria(Long id) {
        return this.categoriaGastosRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> crearCategoria(CategoriaGastos categoriaGastos) {
        CategoriaGastos categoriaEncontrada = categoriaGastosRepository
                .ComprobarCategoriaPorNombre(categoriaGastos.getCategoria());

        if (categoriaEncontrada == null) {
            this.categoriaGastosRepository.save(categoriaGastos);
            return new ResponseEntity<CategoriaGastos>(categoriaGastos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Ya existe esta categoría", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> actualizarCategoria(Long id, CategoriaGastos categoriaGastos) {
        CategoriaGastos categoriaEncontrada = obtenerCategoria(id);

        if (categoriaEncontrada != null) {

            categoriaEncontrada.setCategoria(categoriaGastos.getCategoria());
            this.categoriaGastosRepository.save(categoriaEncontrada);
            return new ResponseEntity<CategoriaGastos>(categoriaEncontrada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No existe esta categoría, deberás crearla primero", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> borrarCategoria(Long id) {
        CategoriaGastos categoriaEncontrada = obtenerCategoria(id);

        if (categoriaEncontrada != null) {
            this.categoriaGastosRepository.delete(categoriaEncontrada);
            return new ResponseEntity<>("Categoría Eliminada! ", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No existe esta categoría", HttpStatus.BAD_REQUEST);
        }
    }
}
