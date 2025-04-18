package backend.Services;

import backend.Entity.Clientes;
import backend.Repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesServices
{
    @Autowired
    private ClientesRepository clientesRepository;




    // Obtener todos los clientes
    public List<Clientes> obtenerTodos()
    {
        return clientesRepository.findAll();
    }


    // Devolver un cliente dado su id
    public Clientes obtenerPorId(Long id)
    {
        return clientesRepository.findById(id).orElse(null);
    }


    // Eliminar a un cliente
    public void eliminarCliente(Long id)
    {
        Clientes clienteEncontrado = obtenerPorId(id);

        if(clienteEncontrado != null)
            clientesRepository.delete(clienteEncontrado);
    }


    //Actualizar a un cliente
    public ResponseEntity<?> actualizarCliente(Clientes cliente, Long id)
    {
        Clientes clienteEncontrado =  obtenerPorId(id);

        if(clienteEncontrado != null)
        {
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
              return ResponseEntity.status(409).body("Cliente no actualizado, revise los datos de nuevo");

    }


    //Crear a un cliente
    public ResponseEntity<?> crearCliente(Clientes cliente)
    {
        Clientes clienteEncontrado = clientesRepository.ComprobarUsuarioPorEmail(cliente.getEmail_cliente());

        if(clienteEncontrado == null)
        {
            clientesRepository.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con éxito!");
        }

        else
            return ResponseEntity.status(409).body("El cliente ya existe");

    }
}
