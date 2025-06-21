package backend.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.Entity.Clientes;
import backend.Repository.ClientesRepository;

@Service
public class ClientesServices {
    @Autowired
    private ClientesRepository clientesRepository;

    /**
     * @description Método para obtener todos los clientes.
     *              Este método devuelve una lista de todos los clientes.
     * @return List<Clientes> Lista de clientes.
     */
    public List<Clientes> obtenerTodos() {
        return clientesRepository.findAll();
    }

    /**
     * @param id de tipo Long
     * @return Clientes
     * @description Método para obtener un cliente por su ID.
     *              Si no se encuentra, devuelve null.
     */
    public Clientes obtenerPorId(Long id) {
        return clientesRepository.findById(id).orElse(null);
    }

    /**
     * @param id de tipo Long
     * @return ResponseEntity<?>
     * @description Método para eliminar un cliente por su ID.
     *              Si el cliente no existe, no hace nada.
     */
    public void eliminarCliente(Long id) {
        Clientes clienteEncontrado = obtenerPorId(id);

        if (clienteEncontrado != null)
            clientesRepository.delete(clienteEncontrado);
    }

    /**
     * @param cliente de tipo Clientes
     * @param id      de tipo Long
     * @return ResponseEntity<?>
     * @description Método para actualizar un cliente.
     *              Si el cliente no existe, devuelve un mensaje de error.
     */
    public ResponseEntity<?> actualizarCliente(Clientes cliente, Long id) {
        Clientes clienteEncontrado = obtenerPorId(id);

        if (clienteEncontrado != null) {
            if (!esEmailValido(cliente.getEmailCliente())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El formato del email no es válido");
            }

            if (cliente.getNombreCliente().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre no puede estar vacío");
            }

            if (cliente.getCifCliente().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El CIF no puede estar vacío");
            }

            // if (!comprobarCif(cliente.getCifCliente(), true, cliente.getId())) {
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El CIF esta ya
            // registrado");
            // }

            if (cliente.getDireccionCliente().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La dirección no puede estar vacía");
            }

            if (cliente.getTelefonoCliente().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El teléfono no puede estar vacío");
            }

            if (cliente.getCiudadCliente().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La ciudad no puede estar vacío");
            }

            if (cliente.getProvinciaCliente().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La provincia no puede estar vacío");
            }

            if (Boolean.FALSE.equals(cliente.getTipoCliente())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debes seleccionar un tipo de cliente");
            }

            clienteEncontrado.setCifCliente(cliente.getCifCliente());
            clienteEncontrado.setNombreCliente(cliente.getNombreCliente());
            clienteEncontrado.setDireccionCliente(cliente.getDireccionCliente());
            clienteEncontrado.setEmailCliente(cliente.getEmailCliente());
            clienteEncontrado.setTelefonoCliente(cliente.getTelefonoCliente());
            clienteEncontrado.setCiudadCliente(cliente.getCiudadCliente());
            clienteEncontrado.setProvinciaCliente(cliente.getProvinciaCliente());
            clienteEncontrado.setTipoCliente(cliente.getTipoCliente());

            clientesRepository.save(clienteEncontrado);
            return ResponseEntity.ok(clienteEncontrado);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cliente no actualizado, revise los datos de nuevo");
        }

    }

    /**
     * @param cliente de tipo Clientes
     * @return ResponseEntity<?>
     * @description Método para crear un nuevo cliente.
     *              Si el cliente ya existe, devuelve un mensaje de error.
     */
    public ResponseEntity<?> crearCliente(Clientes cliente) {
        Clientes clienteEncontrado = clientesRepository.ComprobarClientePorCIF(cliente.getCifCliente());

        if (clienteEncontrado == null) {

            if (!esEmailValido(cliente.getEmailCliente()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El formato del email no es válido");

            if (comprobarCif(cliente.getCifCliente(), false, null)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El CIF ya esta registrado");
            }

            if (comprobarEmailCliente(cliente.getEmailCliente(), false, null)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email ya esta registrado");
            }

            clientesRepository.save(cliente);

            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado con éxito!");

        } else {
            return ResponseEntity.status(409).body("El cliente ya existe");
        }
    }

    /**
     * @param email de tipo String
     * @return boolean
     * @description Método para validar el formato de un email.
     *              Utiliza una expresión regular para comprobar si el email es
     *              válido.
     */
    private boolean esEmailValido(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(EMAIL_REGEX);
    }

    /**
     * @param cif de tipo String
     * @return boolean
     * @description Método para validar el formato de un CIF.
     *              Utiliza una expresión regular para comprobar si el CIF es
     *              válido.
     */
    private boolean comprobarCif(String cif, boolean usuario_editar, Long id) {
        if (usuario_editar) {
            Clientes clienteExistente = clientesRepository.ComprobarClientePorCIF(cif);
            return (clienteExistente != null && !clienteExistente.getId().equals(id));
        }

        return clientesRepository.ComprobarClientePorCIF(cif) != null;
    }

    /**
     * @param email          de tipo String
     * @param usuario_editar de tipo boolean
     * @param id             de tipo Long
     * @return boolean
     * @description Método para comprobar si un email ya está registrado.
     *              Si el usuario está editando, comprueba que el email no
     *              pertenezca
     *              a otro cliente.
     */
    private boolean comprobarEmailCliente(String email, boolean usuario_editar, Long id) {
        if (usuario_editar) {
            Clientes clienteExistente = clientesRepository.ComprobarClientePorEmail(email);
            return (clienteExistente != null && !clienteExistente.getId().equals(id));
        }

        return clientesRepository.ComprobarClientePorEmail(email) != null;
    }
}
