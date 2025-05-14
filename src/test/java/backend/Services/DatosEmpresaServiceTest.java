package backend.Services;

import backend.Entity.DatosEmpresa;
import backend.Repository.DatosEmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatosEmpresaServiceTest {

    private DatosEmpresaRepository datosEmpresaRepository;
    private DatosEmpresaService datosEmpresaService;

    @BeforeEach
    void setUp() {
        datosEmpresaRepository = mock(DatosEmpresaRepository.class);
        datosEmpresaService = new DatosEmpresaService();
        // Use reflection to inject the mock repository
        try {
            java.lang.reflect.Field repoField = DatosEmpresaService.class.getDeclaredField("datosEmpresaRepository");
            repoField.setAccessible(true);
            repoField.set(datosEmpresaService, datosEmpresaRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testObtenerPorId_Found() {
        Long id = 1L;
        DatosEmpresa empresa = new DatosEmpresa();
        when(datosEmpresaRepository.findById(id)).thenReturn(Optional.of(empresa));

        DatosEmpresa result = datosEmpresaService.obtenerPorId(id);

        assertNotNull(result);
        assertEquals(empresa, result);
        verify(datosEmpresaRepository, times(1)).findById(id);
    }

    @Test
    void testObtenerPorId_NotFound() {
        Long id = 2L;
        when(datosEmpresaRepository.findById(id)).thenReturn(Optional.empty());

        DatosEmpresa result = datosEmpresaService.obtenerPorId(id);

        assertNull(result);
        verify(datosEmpresaRepository, times(1)).findById(id);
    }
}