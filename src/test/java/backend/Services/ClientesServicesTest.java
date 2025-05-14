package backend.Services;

import backend.Entity.Clientes;
import backend.Repository.ClientesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientesServicesTest {

    @Mock
    private ClientesRepository clientesRepository;

    @InjectMocks
    private ClientesServices clientesServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosTest() {
        List<Clientes> clientesList = Arrays.asList(new Clientes(), new Clientes());
        when(clientesRepository.findAll()).thenReturn(clientesList);

        List<Clientes> result = clientesServices.obtenerTodos();

        assertEquals(2, result.size());
        verify(clientesRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorIdExisteTest() {
        Clientes cliente = new Clientes();
        cliente.setId(1L);
        when(clientesRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Clientes result = clientesServices.obtenerPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void obtenerPorIdNoExisteTest() {
        when(clientesRepository.findById(2L)).thenReturn(Optional.empty());

        Clientes result = clientesServices.obtenerPorId(2L);

        assertNull(result);
    }

    @Test
    void eliminarClienteExisteTest() {
        Clientes cliente = new Clientes();
        cliente.setId(1L);
        when(clientesRepository.findById(1L)).thenReturn(Optional.of(cliente));

        clientesServices.eliminarCliente(1L);

        verify(clientesRepository, times(1)).delete(cliente);
    }

    @Test
    void eliminarClienteNoExisteTest() {
        when(clientesRepository.findById(2L)).thenReturn(Optional.empty());

        clientesServices.eliminarCliente(2L);

        verify(clientesRepository, never()).delete(any());
    }

    @Test
    void actualizarClienteValidoTest() {
        Clientes clienteExistente = new Clientes();
        clienteExistente.setId(1L);

        Clientes clienteUpdate = new Clientes();
        clienteUpdate.setCif_cliente("CIF123");
        clienteUpdate.setNombre_cliente("Nombre");
        clienteUpdate.setDireccion_cliente("Direccion");
        clienteUpdate.setEmail_cliente("test@email.com");
        clienteUpdate.setTelefono_cliente("123456789");
        clienteUpdate.setFacturas(new ArrayList<>());

        when(clientesRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(clientesRepository.save(any(Clientes.class))).thenReturn(clienteExistente);

        ResponseEntity<?> response = clientesServices.actualizarCliente(clienteUpdate, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(clientesRepository, times(1)).save(clienteExistente);
    }

    @Test
    void actualizarClienteInvalidoEmailTest() {
        Clientes clienteExistente = new Clientes();
        clienteExistente.setId(1L);

        Clientes clienteUpdate = new Clientes();
        clienteUpdate.setEmail_cliente("invalid-email");

        when(clientesRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

        ResponseEntity<?> response = clientesServices.actualizarCliente(clienteUpdate, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El formato del email no es válido", response.getBody());
        verify(clientesRepository, never()).save(any());
    }

    @Test
    void actualizarClienteNoEncontradoTest() {
        Clientes clienteUpdate = new Clientes();
        when(clientesRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = clientesServices.actualizarCliente(clienteUpdate, 2L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cliente no actualizado, revise los datos de nuevo", response.getBody());
    }

    @Test
    void crearClienteValidoTest() {
        Clientes cliente = new Clientes();
        cliente.setCif_cliente("CIF123");
        cliente.setEmail_cliente("test@email.com");

        when(clientesRepository.ComprobarClientePorCIF("CIF123")).thenReturn(null);

        ResponseEntity<?> response = clientesServices.crearCliente(cliente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Cliente creado con éxito!", response.getBody());
        verify(clientesRepository, times(1)).save(cliente);
    }

    @Test
    void crearClienteInvalidoEmailTest() {
        Clientes cliente = new Clientes();
        cliente.setCif_cliente("CIF123");
        cliente.setEmail_cliente("invalid-email");

        when(clientesRepository.ComprobarClientePorCIF("CIF123")).thenReturn(null);

        ResponseEntity<?> response = clientesServices.crearCliente(cliente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El formato del email no es válido", response.getBody());
        verify(clientesRepository, never()).save(any());
    }

    @Test
    void crearClienteYaExisteTest() {
        Clientes cliente = new Clientes();
        cliente.setCif_cliente("CIF123");
        cliente.setEmail_cliente("test@email.com");

        when(clientesRepository.ComprobarClientePorCIF("CIF123")).thenReturn(new Clientes());

        ResponseEntity<?> response = clientesServices.crearCliente(cliente);

        assertEquals(409, response.getStatusCode().value());
        assertEquals("El cliente ya existe", response.getBody());
        verify(clientesRepository, never()).save(any());
    }
}