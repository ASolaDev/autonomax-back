package backend.Services;

import backend.Entity.Clientes;
import backend.Entity.Usuarios;
import backend.Repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientesServices {
    @Autowired
    private ClientesRepository clientesRepository;

    // Obtener todos los clientes
    public List<Clientes> obtenerTodos() {
        return clientesRepository.findAll();
    }

    // Devolver un cliente dado su id
    public Clientes obtenerPorId(Long id) {
        return clientesRepository.findById(id).orElse(null);
    }

    // Eliminar a un cliente
    public void eliminarCliente(Long id) {
        Clientes clienteEncontrado = obtenerPorId(id);

        if (clienteEncontrado != null)
            clientesRepository.delete(clienteEncontrado);
    }

    // Actualizar a un cliente
    public ResponseEntity<?> actualizarCliente(Clientes cliente, Long id) {
        Clientes clienteEncontrado = obtenerPorId(id);

    //Actualizar a un cliente
    public ResponseEntity<?> actualizarCliente(Clientes cliente, Long id)
    {
        Clientes clienteEncontrado =  obtenerPorId(id);

        if(clienteEncontrado != null)
        {
            if(!esEmailValido(cliente.getEmail_cliente()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El formato del email no es válido");



            clienteEncontrado.setCif_cliente(cliente.getCif_cliente());
            clienteEncontrado.setNombre_cliente(cliente.getNombre_cliente());
            clienteEncontrado.setDireccion_cliente(cliente.getDireccion_cliente());
            clienteEncontrado.setEmail_cliente(cliente.getEmail_cliente());
            clienteEncontrado.setTelefono_cliente(cliente.getTelefono_cliente());
            clienteEncontrado.setFacturas(cliente.getFacturas());

            clientesRepository.save(clienteEncontrado);
            return ResponseEntity.ok(clienteEncontrado);


        }
        else
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente no actualizado, revise los datos de nuevo");

    }


    //Crear a un cliente
    public ResponseEntity<?> crearCliente(Clientes cliente)
    {
        Clientes clienteEncontrado = clientesRepository.ComprobarClientePorCIF(cliente.getCif_cliente());

        if(clienteEncontrado == null)
        {
            if(!esEmailValido(cliente.getEmail_cliente()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El formato del email no es válido");


            clientesRepository.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado con éxito!");
        }

        else
            return ResponseEntity.status(409).body("El cliente ya existe");

    }



    private boolean esEmailValido(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(EMAIL_REGEX);
    }

    // Método para comprobar si el CIF ya está registrado
    private boolean comprobarEmail(String cif, boolean usuario_editar, Long id) {
        if (usuario_editar) {
            Clientes clienteExistente = clientesRepository.ComprobarClientePorCIF(cif);
            return (clienteExistente != null && !clienteExistente.getId().equals(id));
        }

        return clientesRepository.ComprobarClientePorCIF(cif) != null;
    }
}
