package fiap.com.br.tds.cp;

import fiap.com.br.tds.cp.controller.CarroController;
import fiap.com.br.tds.cp.domainmodel.Carro;
import fiap.com.br.tds.cp.service.CarroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarroControllerTest {

    @Mock
    private CarroService carroService;

    @InjectMocks
    private CarroController carroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCarros_ShouldReturnAllCarros() {
        Carro carro1 = new Carro(1L, "Ferrari", 2023, 800, 5, "combustão", 2_500_000.0);
        Carro carro2 = new Carro(2L, "Tesla", 2023, 1020, 15, "elétrico", 1_200_000.0);
        when(carroService.getAll()).thenReturn(Arrays.asList(carro1, carro2));

        ResponseEntity<List<Carro>> response = carroController.getAllCarros();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getCarroById_WhenExists_ShouldReturnCarro() {
        Long id = 1L;
        Carro carro = new Carro(id, "Ferrari", 2023, 800, 5, "combustão", 2_500_000.0);
        when(carroService.getById(id)).thenReturn(carro);

        ResponseEntity<Carro> response = carroController.getCarroById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void getCarroById_WhenNotExists_ShouldReturnNotFound() {
        Long id = 99L;
        when(carroService.getById(id)).thenReturn(null);

        ResponseEntity<Carro> response = carroController.getCarroById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createCarro_ShouldReturnCreatedCarro() {
        // Arrange
        Carro novoCarro = new Carro(null, "Porsche", 2023, 650, 8, "combustão", 1_800_000.0);
        Carro carroSalvo = new Carro(3L, "Porsche", 2023, 650, 8, "combustão", 1_800_000.0);
        when(carroService.save(novoCarro)).thenReturn(carroSalvo);

        ResponseEntity<Carro> response = carroController.createCarro(novoCarro);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void updateCarro_WhenExists_ShouldReturnUpdatedCarro() {
        Long id = 1L;
        Carro carroAtualizado = new Carro(id, "Ferrari", 2023, 850, 6, "combustão", 2_600_000.0);
        when(carroService.getById(id)).thenReturn(carroAtualizado);
        when(carroService.update(carroAtualizado)).thenReturn(carroAtualizado);

        ResponseEntity<Carro> response = carroController.updateCarro(id, carroAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(850, response.getBody().getPotencia());
    }

    @Test
    void updateCarro_WhenNotExists_ShouldReturnNotFound() {
        Long id = 99L;
        Carro carroAtualizado = new Carro(id, "Ferrari", 2023, 850, 6, "combustão", 2_600_000.0);
        when(carroService.getById(id)).thenReturn(null);

        ResponseEntity<Carro> response = carroController.updateCarro(id, carroAtualizado);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void partialUpdateCarro_WhenExists_ShouldReturnUpdatedCarro() {
        Long id = 1L;
        Carro carroExistente = new Carro(id, "Ferrari", 2023, 800, 5, "combustão", 2_500_000.0);
        Carro carroAtualizado = new Carro(id, "Ferrari", 2023, 850, 5, "combustão", 2_500_000.0);
        Map<String, Object> updates = Map.of("potencia", 850);

        when(carroService.getById(id)).thenReturn(carroExistente);
        when(carroService.partialUpdate(id, updates)).thenReturn(carroAtualizado);

        ResponseEntity<Carro> response = carroController.partialUpdateCarro(id, updates);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(850, response.getBody().getPotencia());
    }

    @Test
    void deleteCarro_WhenExists_ShouldReturnNoContent() {
        Long id = 1L;
        when(carroService.getById(id)).thenReturn(new Carro());

        ResponseEntity<Void> response = carroController.deleteCarro(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(carroService, times(1)).deleteById(id);
    }

    @Test
    void getTop10ByPotencia_ShouldReturnSortedList() {
        Carro carro1 = new Carro(1L, "Ferrari", 2023, 800, 5, "combustão", 2_500_000.0);
        Carro carro2 = new Carro(2L, "Tesla", 2023, 1020, 15, "elétrico", 1_200_000.0);
        when(carroService.getAll()).thenReturn(Arrays.asList(carro1, carro2));

        ResponseEntity<List<Carro>> response = carroController.getTop10ByPotencia();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1020, response.getBody().get(0).getPotencia());
    }

    @Test
    void getCarrosEletricos_ShouldReturnOnlyEletricos() {
        Carro carro1 = new Carro(1L, "Tesla", 2023, 1020, 15, "elétrico", 1_200_000.0);
        Carro carro2 = new Carro(2L, "Ferrari", 2023, 800, 5, "combustão", 2_500_000.0);
        when(carroService.getAll()).thenReturn(Arrays.asList(carro1, carro2));

        ResponseEntity<List<Carro>> response = carroController.getCarrosEletricos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("elétrico", response.getBody().get(0).getTipo());
    }
}