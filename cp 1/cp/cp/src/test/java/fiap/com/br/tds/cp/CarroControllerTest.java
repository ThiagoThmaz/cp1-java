package fiap.com.br.tds.cp;

import fiap.com.br.tds.cp.controller.CarroController;
import fiap.com.br.tds.cp.datasource.repositories.CarroMockRepository;
import fiap.com.br.tds.cp.domainmodel.Carro;
import fiap.com.br.tds.cp.service.CarroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarroControllerTest {

    @Mock
    private CarroService carroService;

    @InjectMocks
    private CarroController carroController;

    private CarroMockRepository mockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockRepository = new CarroMockRepository();
    }

    @Test
    void getAllCarros_ShouldReturnAllCarros() {
        List<Carro> carros = mockRepository.getAll();
        when(carroService.getAll()).thenReturn(carros);

        ResponseEntity<List<Carro>> response = carroController.getAllCarros();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carros.size(), response.getBody().size());
    }

    @Test
    void getCarroById_WhenExists_ShouldReturnCarro() {
        Long id = 3L;
        Carro carro = mockRepository.findById(id);
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
        Carro novoCarro = new Carro(null, "AUDI", 2023, 500, 600, "elétrico", 220_000.0);
        Carro carroSalvo = mockRepository.save(novoCarro);
        when(carroService.save(novoCarro)).thenReturn(carroSalvo);

        ResponseEntity<Carro> response = carroController.createCarro(novoCarro);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void updateCarro_WhenExists_ShouldReturnUpdatedCarro() {
        Long id = 1L;
        Carro carroAtualizado = new Carro(id, "SIENA", 2023, 210, 420, "combustão", 20_000.0);

        when(carroService.getById(id)).thenReturn(mockRepository.findById(id));
        when(carroService.update(carroAtualizado)).thenReturn(carroAtualizado);

        ResponseEntity<Carro> response = carroController.updateCarro(id, carroAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(210, response.getBody().getPotencia());
    }

    @Test
    void updateCarro_WhenNotExists_ShouldReturnNotFound() {
        Long id = 99L;
        Carro carroAtualizado = new Carro(id, "Modelo", 2023, 100, 100, "elétrico", 100_000.0);
        when(carroService.getById(id)).thenReturn(null);

        ResponseEntity<Carro> response = carroController.updateCarro(id, carroAtualizado);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void partialUpdateCarro_WhenExists_ShouldReturnUpdatedCarro() {
        Long id = 2L;
        Map<String, Object> updates = Map.of("potencia", 650);

        Carro carroExistente = mockRepository.findById(id);
        Carro carroAtualizado = new Carro(
                carroExistente.getId(),
                carroExistente.getModelo(),
                carroExistente.getAno(),
                650,
                carroExistente.getEconomia(),
                carroExistente.getTipo(),
                carroExistente.getPreco()
        );

        when(carroService.getById(id)).thenReturn(carroExistente);
        when(carroService.partialUpdate(id, updates)).thenReturn(carroAtualizado);

        ResponseEntity<Carro> response = carroController.partialUpdateCarro(id, updates);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(650, response.getBody().getPotencia());
    }

    @Test
    void deleteCarro_WhenExists_ShouldReturnNoContent() {
        Long id = 4L;
        when(carroService.getById(id)).thenReturn(mockRepository.findById(id));

        ResponseEntity<Void> response = carroController.deleteCarro(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(carroService, times(1)).deleteById(id);
    }

    @Test
    void getTop10ByPotencia_ShouldReturnSortedList() {
        List<Carro> carros = mockRepository.getAll();
        when(carroService.getAll()).thenReturn(carros);

        ResponseEntity<List<Carro>> response = carroController.getTop10ByPotencia();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody().size());
        assertTrue(response.getBody().get(0).getPotencia() >= response.getBody().get(1).getPotencia());
    }

    @Test
    void getCarrosEletricos_ShouldReturnOnlyEletricos() {
        List<Carro> carros = mockRepository.getAll();
        when(carroService.getAll()).thenReturn(carros);

        ResponseEntity<List<Carro>> response = carroController.getCarrosEletricos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().stream().allMatch(c -> c.getTipo().equalsIgnoreCase("elétrico")));
    }
}
