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

    /**
     * @description Método para obtener todos los proveedores.
     *              Este método devuelve una lista de todos los proveedores.
     * @return List<Proveedores> Lista de proveedores.
     */
    public List<Proveedores> obtenerTodos() {
        return this.proveedoresRepository.findAll();
    }

    /**
     * @param id de tipo Long
     * @return Proveedores
     * @description Método para obtener un proveedor por su ID.
     *              Si no se encuentra, devuelve null.
     */
    public Proveedores obtenerPorId(Long id) {
        return this.proveedoresRepository.findById(id).orElse(null);
    }

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para eliminar un proveedor por su ID.
     *              Si el proveedor no existe, devuelve un mensaje de error.
     */
    public ResponseEntity<?> crearProveedor(Proveedores proveedor) {
        if (proveedoresRepository.ComprobarProveedorPorCIF(proveedor.getCifProveedor()) == null) {

            if (!validarProveedor(proveedor, false, null).getStatusCode().isError()) {

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
                return new ResponseEntity<>(proveedorNuevo, HttpStatus.OK);

            } else {
                return validarProveedor(proveedor, false, null);
            }
        } else {
            return new ResponseEntity<>("Proveedor ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param proveedor de tipo Proveedores
     * @param id        de tipo Long
     * @return ResponseEntity<?>
     * @description Método para actualizar un proveedor.
     *              Si el proveedor no existe, devuelve un mensaje de error.
     */
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

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para eliminar un proveedor por su ID.
     *              Si el proveedor no existe, devuelve un mensaje de error.
     */
    public ResponseEntity<?> borrarProveedor(Long id) {

        Proveedores proveedorEncontrado = obtenerPorId(id);

        if (proveedorEncontrado != null) {
            proveedorEncontrado.getGastos().clear();
            proveedoresRepository.delete(proveedorEncontrado);
            return new ResponseEntity<>("Proveedor borrado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Proveedor no existe", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param proveedor       de tipo Proveedores
     * @param proveedorEditar de tipo Boolean
     * @param idProveedor     de tipo Long
     * @return ResponseEntity<?>
     * @description Método para validar los datos de un proveedor.
     *              Comprueba que el email, nombre, CIF, dirección, teléfono,
     *              ciudad, provincia y tipo sean válidos.
     */
    public ResponseEntity<?> validarProveedor(Proveedores proveedor, Boolean proveedorEditar, Long idProveedor) {

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
            if (comprobarCif(proveedor.getCifProveedor(), false, null)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El CIF ya está registrado");
            }

            if (comprobarEmailProveedor(proveedor.getEmailProveedor(), false, null)) {
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

        if (!proveedor.getTipoProveedor().equals(TipoProveedor.Empresa)
                && !proveedor.getTipoProveedor().equals(TipoProveedor.Autonomo)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tipo debe ser Empresa o Autónomo");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param email de tipo String
     * @return boolean
     * @description Método para validar el formato de un email.
     *              Utiliza una expresión regular para comprobar el formato.
     */
    private boolean esEmailValido(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(EMAIL_REGEX);
    }

    /**
     * @param cif de tipo String
     * @return boolean
     * @description Método para validar el formato de un CIF.
     *              Utiliza una expresión regular para comprobar el formato.
     */
    private boolean comprobarCif(String cif, boolean proveedor_editar, Long id) {

        if (proveedor_editar) {
            Proveedores proveedorExistente = proveedoresRepository.ComprobarProveedorPorCIF(cif);
            return (proveedorExistente != null && !proveedorExistente.getId().equals(id));
        }

        return proveedoresRepository.ComprobarProveedorPorCIF(cif) != null;
    }

    /**
     * @param email            de tipo String
     * @param proveedor_editar de tipo boolean
     * @param id               de tipo Long
     * @return boolean
     * @description Método para comprobar si un email ya está registrado.
     *              Si el proveedor está siendo editado, comprueba que el email no
     *              pertenezca a otro proveedor.
     */
    private boolean comprobarEmailProveedor(String email, boolean proveedor_editar, Long id) {

        if (proveedor_editar) {
            Proveedores proveedorExistente = proveedoresRepository.ComprobarProveedorPorEmail(email);
            return (proveedorExistente != null && !proveedorExistente.getId().equals(id));
        }

        return proveedoresRepository.ComprobarProveedorPorEmail(email) != null;
    }

}
