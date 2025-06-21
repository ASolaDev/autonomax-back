package backend.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import backend.DTOS.EditarFacturaDetallesDTO;
import backend.DTOS.FacturaDetallesDTO;
import backend.Entity.Clientes;
import backend.Entity.DatosEmpresa;
import backend.Entity.Facturas;
import backend.Entity.Usuarios;
import backend.Repository.ClientesRepository;
import backend.Repository.DatosEmpresaRepository;
import backend.Repository.FacturasRepository;
import backend.Repository.UsuariosRepository;

@Service
public class FacturasService {

    @Autowired
    private FacturasRepository facturasRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private DatosEmpresaRepository datosEmpresaRepository;

    @Autowired
    private DetalleFacturaService detalleFacturaService;

    /**
     * @param numeroFactura de tipo String
     * @return Facturas
     * @description Método para encontrar una factura por su número de factura.
     *              Si no se encuentra, devuelve null.
     */
    public Facturas encontrarFacturaPorNumeroFactura(String numeroFactura) {
        return facturasRepository.ObtenerFacturaPorNumeroFactura(numeroFactura);
    }

    /**
     * @param idUsuario de tipo Long
     * @return List<Facturas>
     * @description Método para obtener todas las facturas de un usuario por su ID.
     *              Si el usuario no existe, devuelve null.
     */
    public List<Facturas> obtenerFacturasPorUsuario(Long idUsuario) {

        if (usuariosService.obtenerUsuarioPorID(idUsuario) != null) {
            return facturasRepository.ObtenerFacturasPorIdUsuario(idUsuario);
        } else {
            return null;
        }

    }

    /**
     * @return List<Facturas>
     * @description Método para obtener todas las facturas.
     */
    public List<Facturas> obtenerTodas() {
        return facturasRepository.findAll();
    }

    /**
     * @param id de tipo Long
     * @return Facturas
     * @description Método para buscar una factura por su ID. Si no se encuentra,
     *              devuelve null.
     */
    public Facturas buscarFacturaPorId(Long id) {
        return facturasRepository.findById(id).orElse(null);
    }

    /**
     * @param facturaJson de tipo FacturaDetallesDTO
     * @return ResponseEntity<?>
     * @description Método para crear una nueva factura. Si el número de factura ya
     *              existe, devuelve un mensaje de error. Si el usuario, cliente o
     *              empresa no existen, devuelve un mensaje de error.
     */
    public ResponseEntity<?> crearFactura(@RequestBody FacturaDetallesDTO facturaJson) {

        if (facturaJson.getNumeroFactura() == null || facturaJson.getNumeroFactura().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay un número de factura");
        }

        if (facturasRepository.ObtenerFacturaPorNumeroFactura(facturaJson.getNumeroFactura()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número de factura duplicado");
        }

        // Buscar entidades por ID
        Usuarios usuEncontrado = usuariosRepository.findById(facturaJson.getIdUsuario()).orElse(null);
        if (usuEncontrado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");
        }

        Clientes clienteEncontrado = clientesRepository.findById(facturaJson.getIdCliente()).orElse(null);
        if (clienteEncontrado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente no existe");
        }

        DatosEmpresa empresaEncontrada = datosEmpresaRepository.findById(facturaJson.getIdEmpresa()).orElse(null);
        if (empresaEncontrada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La empresa no existe");
        }

        if (facturaJson.getFacturasDetalles().size() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se han incluido detalles en la factura");
        }

        Facturas facturaNueva = new Facturas();

        facturaNueva.setNumeroFactura(facturaJson.getNumeroFactura());
        facturaNueva.setEstado(facturaJson.getEstado());
        facturaNueva.setFechaEmision(facturaJson.getFechaEmision());
        facturaNueva.setFechaPago(facturaJson.getFechaPago());
        facturaNueva.setIva(facturaJson.getIva());
        facturaNueva.setSubtotal(facturaJson.getSubtotal());
        facturaNueva.setTotal(facturaJson.getTotal());
        facturaNueva.setCliente(clienteEncontrado);
        facturaNueva.setUsuario(usuEncontrado);
        facturaNueva.setEmpresa(empresaEncontrada);

        facturasRepository.save(facturaNueva);

        Facturas facturaDetalles = encontrarFacturaPorNumeroFactura(facturaNueva.getNumeroFactura());

        if (facturaDetalles != null) {
            detalleFacturaService.crearDetalleFactura(facturaJson.getFacturasDetalles(), facturaDetalles);
            return ResponseEntity.status(HttpStatus.CREATED).body("Factura creada");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha encontrado la factura");
        }
    }

    /**
     * @param id          de tipo Long
     * @param facturaJson de tipo EditarFacturaDetallesDTO
     * @return ResponseEntity<?>
     * @description Método para actualizar una factura por su ID. Si la factura no
     *              existe, devuelve un mensaje de error.
     */
    public ResponseEntity<?> actualizarFactura(Long id, EditarFacturaDetallesDTO facturaJson) {
        System.out.println(facturaJson);
        if (facturaJson.getNumeroFactura() == null || facturaJson.getNumeroFactura().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay un número de factura");
        }

        Clientes clienteEncontrado = clientesRepository.findById(facturaJson.getCliente().getId()).orElse(null);
        if (clienteEncontrado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente no existe");
        }

        Facturas facturaEncontrada = buscarFacturaPorId(id);
        if (facturaEncontrada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La factura " + facturaJson.getNumeroFactura() + " no existe");
        }

        System.out.println("hola");

        if (facturaJson.getFacturasDetalles().size() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se han incluido detalles en la factura");
        }
        facturaEncontrada.setEstado(facturaJson.getEstado());
        facturaEncontrada.setFechaEmision(facturaJson.getFechaEmision());
        facturaEncontrada.setIva(facturaJson.getIva());
        facturaEncontrada.setSubtotal(facturaJson.getSubtotal());
        facturaEncontrada.setFechaPago(facturaJson.getFechaPago());
        facturaEncontrada.setTotal(facturaJson.getTotal());
        facturaEncontrada.setCliente(clienteEncontrado);

        facturasRepository.save(facturaEncontrada);

        detalleFacturaService.crearDetalleFactura(facturaJson.getFacturasDetalles(), facturaEncontrada);

        return ResponseEntity.status(HttpStatus.CREATED).body("Factura actualizada");
    }

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para borrar una factura por su ID. Si la factura no
     *              existe, devuelve un mensaje de error.
     */
    public ResponseEntity<?> borrarFactura(Long id) {
        Facturas factura = facturasRepository.findById(id).orElse(null);

        if (factura == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La factura no se ha encontrado");
        } else {
            facturasRepository.delete(factura);
            return ResponseEntity.status(HttpStatus.OK).body("Factura eliminada");
        }

    }

}
