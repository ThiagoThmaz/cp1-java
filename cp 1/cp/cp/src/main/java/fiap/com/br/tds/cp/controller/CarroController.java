package fiap.com.br.tds.cp.controller;

import fiap.com.br.tds.cp.domainmodel.Carro;
import fiap.com.br.tds.cp.service.CarroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carros")
public class CarroController {

    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping
    public ResponseEntity<List<Carro>> getAllCarros() {
        List<Carro> carros = carroService.getAll();
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> getCarroById(@PathVariable Long id) {
        Carro carro = carroService.getById(id);
        if (carro != null) {
            return new ResponseEntity<>(carro, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Carro> createCarro(@RequestBody Carro carro) {
        Carro novoCarro = carroService.save(carro);
        return new ResponseEntity<>(novoCarro, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> updateCarro(@PathVariable Long id, @RequestBody Carro carro) {
        Carro existingCarro = carroService.getById(id);
        if (existingCarro == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carro.setId(id);
        Carro updatedCarro = carroService.update(carro);
        return new ResponseEntity<>(updatedCarro, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Carro> partialUpdateCarro(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Carro existingCarro = carroService.getById(id);
        if (existingCarro == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Carro updatedCarro = carroService.partialUpdate(id, updates);
        return new ResponseEntity<>(updatedCarro, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarro(@PathVariable Long id) {
        Carro existingCarro = carroService.getById(id);
        if (existingCarro == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carroService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/potencia")
    public ResponseEntity<List<Carro>> getTop10ByPotencia() {
        List<Carro> carros = carroService.getAll();
        carros.sort((c1, c2) -> Integer.compare(c2.getPotencia(), c1.getPotencia()));
        List<Carro> top10 = carros.stream().limit(10).toList();
        return new ResponseEntity<>(top10, HttpStatus.OK);
    }

    @GetMapping("/economia")
    public ResponseEntity<List<Carro>> getTop10ByEconomia() {
        List<Carro> carros = carroService.getAll();
        carros.sort((c1, c2) -> Integer.compare(c2.getEconomia(), c1.getEconomia()));
        List<Carro> top10 = carros.stream().limit(10).toList();
        return new ResponseEntity<>(top10, HttpStatus.OK);
    }

    @GetMapping("/eletricos")
    public ResponseEntity<List<Carro>> getCarrosEletricos() {
        List<Carro> carros = carroService.getAll();
        List<Carro> eletricos = carros.stream()
                .filter(c -> "elétrico".equalsIgnoreCase(c.getTipo()))
                .toList();
        return new ResponseEntity<>(eletricos, HttpStatus.OK);
    }
}