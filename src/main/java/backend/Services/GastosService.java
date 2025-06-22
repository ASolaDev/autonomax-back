package backend.Services;

import backend.DTOS.GastosDTO;
import backend.Entity.*;
import backend.Repository.GastosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class GastosService {

    @Autowired
    private GastosRepository gastosRepository;

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private FacturasService facturasService;

    @Autowired
    private ProveedoresService proveedoresService;

    @Autowired
    private CategoriaGastosService categoriaGastosService;

    /**
     * Obtener todos los gastos
     * 
     * @return lista de gastos
     */
    public List<Gastos> obtenerTodos() {
        return gastosRepository.findAll();
    }

    /**
     * Obtener todos los gastos por ID de usuario
     * 
     * @param idUsuario ID del usuario
     * @return lista de gastos del usuario
     */
    public List<Gastos> obtenerTodosPorIdUsuario(@RequestParam Long idUsuario) {
        return gastosRepository.ObtenerGastosPorIdUsuario(idUsuario);
    }

    /**
     * Obtener un gasto por ID
     * 
     * @param id ID del gasto
     * @return gasto encontrado o null si no existe
     */
    public Gastos obtenerPorId(Long id) {
        return gastosRepository.findById(id).orElse(null);
    }

    /**
     * Crear un nuevo gasto
     * 
     * @param gastosDTO DTO con los datos del gasto
     * @return ResponseEntity con el resultado de la operación
     */
    public ResponseEntity<?> crearGasto(GastosDTO gastosDTO) {

        Usuarios usuarioEncontrado = usuariosService.obtenerUsuarioPorID(gastosDTO.getUsuario());

        if (usuarioEncontrado == null) {
            return new ResponseEntity<>("USUARIO NO ENCONTRADO", HttpStatus.BAD_REQUEST);
        }

        Proveedores proveedorEncontrado = proveedoresService.obtenerPorId(gastosDTO.getProveedor());

        if (proveedorEncontrado == null) {
            return new ResponseEntity<>("PROVEEDOR NO ENCONTRADO", HttpStatus.BAD_REQUEST);
        }

        Facturas facturaEncontrada = facturasService.buscarFacturaPorId(gastosDTO.getFactura());

        if (facturaEncontrada == null) {
            return new ResponseEntity<>("FACTURA NO ENCONTRADO", HttpStatus.BAD_REQUEST);

        }

        CategoriaGastos categoriaGastos = categoriaGastosService.obtenerCategoria(gastosDTO.getCategoria());

        if (categoriaGastos == null) {
            return new ResponseEntity<>("La categoría no se ha encontrado", HttpStatus.BAD_REQUEST);

        }

        if (gastosDTO.getDescripcion().trim().isEmpty()) {
            return new ResponseEntity<>("Descripción vacía", HttpStatus.BAD_REQUEST);
        }

        if (gastosDTO.getFecha() == null) {
            return new ResponseEntity<>("Fecha vacía", HttpStatus.BAD_REQUEST);
        }

        if (gastosDTO.getMonto() == null) {
            return new ResponseEntity<>("Monto vacío", HttpStatus.BAD_REQUEST);
        }

        if (gastosDTO.getMetodoPago() == null) {
            return new ResponseEntity<>("Método de pago vacío", HttpStatus.BAD_REQUEST);
        } else {
            if (!gastosDTO.getMetodoPago().equals(MetodoPago.Tarjeta)
                    && !gastosDTO.getMetodoPago().equals(MetodoPago.Acuenta)
                    && !gastosDTO.getMetodoPago().equals(MetodoPago.Efectivo)) {
                return new ResponseEntity<>("El método de pago debe ser Tarjeta, Acuenta o Efectivo",
                        HttpStatus.BAD_REQUEST);

            }
        }

        Gastos nuevoGasto = new Gastos();
        nuevoGasto.setFecha(gastosDTO.getFecha());
        nuevoGasto.setMonto(gastosDTO.getMonto());
        nuevoGasto.setDescripcion(gastosDTO.getDescripcion());
        nuevoGasto.setUsuario(usuarioEncontrado);
        nuevoGasto.setFactura(facturaEncontrada);
        nuevoGasto.setProveedor(proveedorEncontrado);
        nuevoGasto.setCategoria(categoriaGastos);
        nuevoGasto.setMetodoPago(gastosDTO.getMetodoPago());
        gastosRepository.save(nuevoGasto);

        return new ResponseEntity<>("Gasto guardado", HttpStatus.OK);
    }

    /**
     * Borrar un gasto por ID
     * 
     * @param id ID del gasto
     * @return ResponseEntity con el resultado de la operación
     */
    public ResponseEntity<?> borrarGasto(Long id) {
        Gastos gastoEncontrado = obtenerPorId(id);
        if (gastoEncontrado != null) {
            gastosRepository.delete(gastoEncontrado);
            return new ResponseEntity<>("Gasto borrado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Gasto no existe", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualizar un gasto por ID
     * 
     * @param gastosDTO DTO con los datos del gasto
     * @param id        ID del gasto a actualizar
     * @return ResponseEntity con el resultado de la operación
     */
    public ResponseEntity<?> actualizarGasto(GastosDTO gastosDTO, Long id) {

        Gastos gastoEncontrado = obtenerPorId(id);

        if (gastoEncontrado != null) {

            Usuarios usuarioEncontrado = usuariosService.obtenerUsuarioPorID(gastosDTO.getUsuario());

            if (usuarioEncontrado == null) {
                return new ResponseEntity<>("USUARIO NO ENCONTRADO", HttpStatus.BAD_REQUEST);
            }

            Proveedores proveedorEncontrado = proveedoresService.obtenerPorId(gastosDTO.getProveedor());

            if (proveedorEncontrado == null) {
                return new ResponseEntity<>("PROVEEDOR NO ENCONTRADO", HttpStatus.BAD_REQUEST);
            }

            Facturas facturaEncontrada = facturasService.buscarFacturaPorId(gastosDTO.getFactura());

            if (facturaEncontrada == null) {
                return new ResponseEntity<>("FACTURA NO ENCONTRADO", HttpStatus.BAD_REQUEST);

            }

            CategoriaGastos categoriaGastos = categoriaGastosService.obtenerCategoria(gastosDTO.getCategoria());

            if (categoriaGastos == null) {
                return new ResponseEntity<>("La categoría no se ha encontrado", HttpStatus.BAD_REQUEST);

            }

            gastoEncontrado.setFecha(gastosDTO.getFecha());
            gastoEncontrado.setDescripcion(gastosDTO.getDescripcion());
            gastoEncontrado.setMonto(gastosDTO.getMonto());
            gastoEncontrado.setMetodoPago(gastosDTO.getMetodoPago());
            gastoEncontrado.setFactura(facturaEncontrada);
            gastoEncontrado.setProveedor(proveedorEncontrado);
            gastoEncontrado.setUsuario(usuarioEncontrado);
            gastoEncontrado.setCategoria(categoriaGastos);
            gastosRepository.save(gastoEncontrado);

            return new ResponseEntity<>("Gasto guardado!", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Gasto no existe", HttpStatus.BAD_REQUEST);
        }
    }
}
