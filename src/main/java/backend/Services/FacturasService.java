package backend.Services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    private ClientesRepository clientesRepository;

    @Autowired
    private DatosEmpresaRepository datosEmpresaRepository;

    @Autowired
    private DetalleFacturaService detalleFacturaService;

    public Facturas encontrarFacturaPorNumeroFactura(String numeroFactura) {
        return facturasRepository.ObtenerFacturaPorNumeroFactura(numeroFactura);
    }

    public List<Facturas> obtenerTodas() {
        return facturasRepository.findAll();
    }

    public Facturas buscarFacturaPorId(Long id) {
        return facturasRepository.findById(id).orElse(null);
    }


    public ResponseEntity<?> crearFactura(@RequestBody FacturaDetallesDTO facturaJson) {

        // 1. Comprobamos que los campos que no tienen que ser nulos, no lo sean
        if (facturaJson.getNumeroFactura() == "")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay un número de factura");

        if (facturasRepository.ObtenerFacturaPorNumeroFactura(facturaJson.getNumeroFactura()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número de factura duplicado");

        /*
         * Hacer lo siguiente en los repositorios
         * Buscar al cliente por su CIF
         * Buscar al usuario por su email
         * Buscar a la empresa por su nombre (solo tenemos 1)
         */

        Usuarios usuEncontrado = usuariosRepository.ComprobarUsuarioPorEmail(facturaJson.getEmailUsuario());

        if (usuEncontrado == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");

        Clientes clienteEncontrado = clientesRepository.ComprobarClientePorCIF(facturaJson.getCifCliente());

        if (clienteEncontrado == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente no existe");

        DatosEmpresa empresaEncontrada = datosEmpresaRepository
                .ComprobarEmpresaPorNombre(facturaJson.getNombreEmpresa());

        if (empresaEncontrada == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de la empresa no existe");

        if (!facturaJson.getIva().equals(new BigDecimal(21.00)) && !facturaJson.getIva().equals(new BigDecimal(10.00))
                && !facturaJson.getIva().equals(new BigDecimal(4.00))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IVA tiene que ser 21, 10 o 4");

        }

        if (facturaJson.getFacturasDetalles().size() <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se han incluido detalles en la factura");

        // 2. Si todo esta correcto, procedemos a meter todo en la BD

        Facturas facturaNueva = new Facturas();

        facturaNueva.setNumero_factura(facturaJson.getNumeroFactura());
        facturaNueva.setEstado(facturaJson.getEstado());
        facturaNueva.setFecha_emision(facturaJson.getFechaEmision());
        facturaNueva.setFecha_pago(facturaJson.getFechaPago());
        facturaNueva.setIva(facturaJson.getIva());
        facturaNueva.setSubtotal(facturaJson.getSubtotal());
        facturaNueva.setTotal(facturaJson.getTotal());
        facturaNueva.setCliente(clienteEncontrado);
        facturaNueva.setUsuario(usuEncontrado);
        facturaNueva.setEmpresa(empresaEncontrada);

        facturasRepository.save(facturaNueva);

        // Al crear la factura, podemos agregar los detalles (se comprobó arriba que si
        // no hay detalles, de error)

        Facturas facturaDetalles = encontrarFacturaPorNumeroFactura(facturaNueva.getNumero_factura());

        if (facturaDetalles != null) {
            detalleFacturaService.crearDetalleFactura(facturaJson.getFacturasDetalles(), facturaDetalles);

            return ResponseEntity.status(HttpStatus.CREATED).body("Factura creada");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha encontrado la factura");
        }

    }

    public ResponseEntity<?> actualizarFactura(Long id, FacturaDetallesDTO facturaJson) {
        // 1. Comprobar que los campos no son nulos y la factura EXISTE

        if (facturaJson.getNumeroFactura() == "" || facturaJson.getNumeroFactura().trim().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay un número de factura");

        Usuarios usuEncontrado = usuariosRepository.ComprobarUsuarioPorEmail(facturaJson.getEmailUsuario());

        if (usuEncontrado == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");

        Clientes clienteEncontrado = clientesRepository.ComprobarClientePorCIF(facturaJson.getCifCliente());

        if (clienteEncontrado == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente no existe");

        DatosEmpresa empresaEncontrada = datosEmpresaRepository
                .ComprobarEmpresaPorNombre(facturaJson.getNombreEmpresa());

        if (empresaEncontrada == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de la empresa no existe");

        Facturas facturaEncontrada = buscarFacturaPorId(id);

        if (facturaEncontrada == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La factura" + facturaJson.getNumeroFactura() + " no existe");

        if (!facturaJson.getIva().equals(new BigDecimal(21.00)) && !facturaJson.getIva().equals(new BigDecimal(10.00))
                && !facturaJson.getIva().equals(new BigDecimal(4.00))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IVA tiene que ser 21, 10 o 4");

        }

        if (facturaJson.getFacturasDetalles().size() <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se han incluido detalles en la factura");

        // 2. Si todo es correcto, actualizamos

        facturaEncontrada.setEstado(facturaJson.getEstado());
        facturaEncontrada.setFecha_emision(facturaJson.getFechaEmision());
        facturaEncontrada.setIva(facturaJson.getIva());
        facturaEncontrada.setSubtotal(facturaJson.getSubtotal());
        facturaEncontrada.setFecha_pago(facturaJson.getFechaPago());
        facturaEncontrada.setTotal(facturaJson.getTotal());
        facturaEncontrada.setCliente(clienteEncontrado);
        facturaEncontrada.setUsuario(usuEncontrado);
        facturaEncontrada.setEmpresa(empresaEncontrada);
        facturasRepository.save(facturaEncontrada);

        detalleFacturaService.crearDetalleFactura(facturaJson.getFacturasDetalles(), facturaEncontrada);

        return ResponseEntity.status(HttpStatus.CREATED).body(" Factura actualizada ");

    }

    public ResponseEntity<?> borrarFactura(Long id) {
        Facturas factura = facturasRepository.findById(id).orElse(null);

        if (factura == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La factura no se ha encontrado");
        else {
            facturasRepository.delete(factura);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Factura eliminada");

        }

    }

}
