package backend.Services;

import backend.Entity.Proveedores;
import backend.Entity.TipoProveedor;
import backend.Repository.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedoresService {

    @Autowired
    private ProveedoresRepository proveedoresRepository;


    public List<Proveedores> obtenerTodos(){
        return this.proveedoresRepository.findAll();
    }

    public Proveedores obtenerPorId(Long id){
        return this.proveedoresRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> crearProveedor(Proveedores proveedor){
        if(proveedoresRepository.ComprobarProveedorPorCIF(proveedor.getCifProveedor()) == null){
            if(!validarProveedor(proveedor,false,null).getStatusCode().isError()){
                Proveedores proveedorNuevo = new Proveedores();
                proveedorNuevo.setNombreProveedor(proveedor.getNombreProveedor().trim());
                proveedorNuevo.setCifProveedor(proveedor.getCifProveedor());
                proveedorNuevo.setDireccionProveedor(proveedor.getDireccionProveedor().trim());
                proveedorNuevo.setCiudadProveedor(proveedor.getCiudadProveedor().trim());
                proveedorNuevo.setTelefonoProveedor(proveedor.getTelefonoProveedor().trim());
                proveedorNuevo.setEmailProveedor(proveedor.getEmailProveedor().trim());
                proveedorNuevo.setProvinciaProveedor(proveedor.getProvinciaProveedor().trim());
                proveedorNuevo.setTipoProveedor(proveedor.getTipoProveedor());
                proveedoresRepository.save(proveedorNuevo);
                return new ResponseEntity<>(proveedorNuevo,HttpStatus.OK);

            }
            else{
               return validarProveedor(proveedor,false,null);
            }
        }
        else{
            return new ResponseEntity<>("Proveedor ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> actualizarProveedor(Proveedores proveedor, Long id) {
        Proveedores proveedorEncontrado = obtenerPorId(id);

        if (proveedorEncontrado == null) {
            return new ResponseEntity<>("Proveedor no existe", HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<?> validacion = validarProveedor(proveedor, true, id);
        if (validacion.getStatusCode().isError()) {
            return validacion;
        }

        proveedorEncontrado.setNombreProveedor(proveedor.getNombreProveedor().trim());
        proveedorEncontrado.setCifProveedor(proveedorEncontrado.getCifProveedor().trim());
        proveedorEncontrado.setDireccionProveedor(proveedor.getDireccionProveedor().trim());
        proveedorEncontrado.setCiudadProveedor(proveedor.getCiudadProveedor().trim());
        proveedorEncontrado.setTelefonoProveedor(proveedor.getTelefonoProveedor().trim());
        proveedorEncontrado.setEmailProveedor(proveedor.getEmailProveedor().trim());
        proveedorEncontrado.setProvinciaProveedor(proveedor.getProvinciaProveedor().trim());
        proveedorEncontrado.setTipoProveedor(proveedor.getTipoProveedor());

        proveedoresRepository.save(proveedorEncontrado);
        return new ResponseEntity<>(proveedorEncontrado, HttpStatus.OK);
    }

    public ResponseEntity<?> borrarProveedor(Long id){
        Proveedores proveedorEncontrado = obtenerPorId(id);
        if(proveedorEncontrado != null){
            proveedorEncontrado.getGastos().clear();
            proveedoresRepository.delete(proveedorEncontrado);
            return new ResponseEntity<>("Proveedor borrado",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Proveedor no existe",HttpStatus.BAD_REQUEST);
        }
    }

    // ********** Métodos auxiliares *********************


    public ResponseEntity<?> validarProveedor(Proveedores proveedor, Boolean proveedorEditar, Long idProveedor){
        if (!esEmailValido(proveedor.getEmailProveedor())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El formato del email no es válido");
        }

        if (proveedor.getNombreProveedor().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre no puede estar vacío");
        }

        if (!proveedorEditar && proveedor.getCifProveedor().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El CIF no puede estar vacío");
        }

        if (!proveedorEditar) {
            if(comprobarCif(proveedor.getCifProveedor(), false, null)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El CIF ya está registrado");
            }
            if(comprobarEmailProveedor(proveedor.getEmailProveedor(), false,null)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email esta duplicado");
            }
        }



        if (proveedor.getDireccionProveedor().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La dirección no puede estar vacía");
        }

        if (proveedor.getTelefonoProveedor().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El teléfono no puede estar vacío");
        }

        if (proveedor.getCiudadProveedor().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La ciudad no puede estar vacío");
        }

        if (proveedor.getProvinciaProveedor().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La provincia no puede estar vacío");
        }

        if (!proveedor.getTipoProveedor().equals(TipoProveedor.Empresa) && !proveedor.getTipoProveedor().equals(TipoProveedor.Autonomo)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tipo debe ser Empresa o Autónomo");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private boolean esEmailValido(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(EMAIL_REGEX);
    }

    private boolean comprobarCif(String cif, boolean proveedor_editar, Long id) {
        if (proveedor_editar) {
            Proveedores proveedorExistente = proveedoresRepository.ComprobarProveedorPorCIF(cif);
            return (proveedorExistente != null && !proveedorExistente.getId().equals(id));
        }

        return proveedoresRepository.ComprobarProveedorPorCIF(cif) != null;
    }

    private boolean comprobarEmailProveedor(String email, boolean proveedor_editar, Long id) {
        if (proveedor_editar) {
            Proveedores proveedorExistente = proveedoresRepository.ComprobarProveedorPorEmail(email);
            return (proveedorExistente != null && !proveedorExistente.getId().equals(id));
        }

        return proveedoresRepository.ComprobarProveedorPorEmail(email) != null;
    }

    // *****************************************************************
}
