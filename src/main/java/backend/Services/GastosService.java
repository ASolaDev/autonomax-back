package backend.Services;

import backend.DTOS.GastosDTO;
import backend.Entity.*;
import backend.Repository.GastosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastosService{

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

    public List<Gastos>obtenerTodos(){
        return gastosRepository.findAll();
    }

    public Gastos obtenerPorId(Long id){
        return gastosRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> crearGasto(GastosDTO gastosDTO){

        // 1. Localizar el usuario, la factura ,el proveedor y la categoría si no están los 4 hay un error
        Usuarios usuarioEncontrado = usuariosService.obtenerUsuarioPorID(gastosDTO.getUsuario());

        if(usuarioEncontrado ==null){
            return new ResponseEntity<>("USUARIO NO ENCONTRADO", HttpStatus.BAD_REQUEST);
        }

        Proveedores proveedorEncontrado = proveedoresService.obtenerPorId(gastosDTO.getProveedor());

        if(proveedorEncontrado == null){
            return new ResponseEntity<>("PROVEEDOR NO ENCONTRADO", HttpStatus.BAD_REQUEST);
        }

        Facturas facturaEncontrada = facturasService.buscarFacturaPorId(gastosDTO.getFactura());

        if(facturaEncontrada == null){
            return new ResponseEntity<>("FACTURA NO ENCONTRADO", HttpStatus.BAD_REQUEST);

        }

        CategoriaGastos categoriaGastos = categoriaGastosService.obtenerCategoria(gastosDTO.getCategoria());

        if(categoriaGastos == null){
            return new ResponseEntity<>("La categoría no se ha encontrado", HttpStatus.BAD_REQUEST);

        }

        //2. Si se ha encontrado todo, procedemos a validar, como he tenido problemas con la función en proveedores
        // repetiré el código en ambos sitios de forma temporal

        if(gastosDTO.getDescripcion().trim().isEmpty()){
            return new ResponseEntity<>("Descripción vacía", HttpStatus.BAD_REQUEST);
        }

        if(gastosDTO.getFecha() == null){
            return new ResponseEntity<>("Fecha vacía", HttpStatus.BAD_REQUEST);
        }

        if(gastosDTO.getMonto() == null){
            return new ResponseEntity<>("Monto vacío", HttpStatus.BAD_REQUEST);
        }

        if(gastosDTO.getMetodoPago() == null){
            return new ResponseEntity<>("Método de pago vacío", HttpStatus.BAD_REQUEST);
        }
        else{
            if(!gastosDTO.getMetodoPago().equals(MetodoPago.Tarjeta) && !gastosDTO.getMetodoPago().equals(MetodoPago.Acuenta)
            && !gastosDTO.getMetodoPago().equals(MetodoPago.Efectivo)){
                return new ResponseEntity<>("El método de pago debe ser Tarjeta, Acuenta o Efectivo", HttpStatus.BAD_REQUEST);

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

    public ResponseEntity<?> borrarGasto(Long id){
        Gastos gastoEncontrado = obtenerPorId(id);
        if(gastoEncontrado != null){
            gastosRepository.delete(gastoEncontrado);
            return new ResponseEntity<>("Gasto borrado",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Gasto no existe",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> actualizarGasto(GastosDTO gastosDTO, Long id){

        //1. Comprobar que el gasto existe, en caso contrario informar

        Gastos gastoEncontrado = obtenerPorId(id);

        if(gastoEncontrado != null){

            //2. Si la encuentra, debemos ver que todo lo necesario lo encuentra

            Usuarios usuarioEncontrado = usuariosService.obtenerUsuarioPorID(gastosDTO.getUsuario());

            if(usuarioEncontrado ==null){
                return new ResponseEntity<>("USUARIO NO ENCONTRADO", HttpStatus.BAD_REQUEST);
            }

            Proveedores proveedorEncontrado = proveedoresService.obtenerPorId(gastosDTO.getProveedor());

            if(proveedorEncontrado == null){
                return new ResponseEntity<>("PROVEEDOR NO ENCONTRADO", HttpStatus.BAD_REQUEST);
            }

            Facturas facturaEncontrada = facturasService.buscarFacturaPorId(gastosDTO.getFactura());

            if(facturaEncontrada == null){
                return new ResponseEntity<>("FACTURA NO ENCONTRADO", HttpStatus.BAD_REQUEST);

            }

            CategoriaGastos categoriaGastos = categoriaGastosService.obtenerCategoria(gastosDTO.getCategoria());

            if(categoriaGastos == null){
                return new ResponseEntity<>("La categoría no se ha encontrado", HttpStatus.BAD_REQUEST);

            }


            // 3. Guardar todo y actualizar
            gastoEncontrado.setFecha(gastosDTO.getFecha());
            gastoEncontrado.setDescripcion(gastosDTO.getDescripcion());
            gastoEncontrado.setMonto(gastosDTO.getMonto());
            gastoEncontrado.setMetodoPago(gastosDTO.getMetodoPago());
            gastoEncontrado.setFactura(facturaEncontrada);
            gastoEncontrado.setUsuario(usuarioEncontrado);
            gastoEncontrado.setCategoria(categoriaGastos);
            gastosRepository.save(gastoEncontrado);

            return new ResponseEntity<>(gastoEncontrado,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Gasto no existe",HttpStatus.BAD_REQUEST);
        }
    }
}
