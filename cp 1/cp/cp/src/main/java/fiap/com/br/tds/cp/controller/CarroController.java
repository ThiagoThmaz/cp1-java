package fiap.com.br.tds.cp.controller;

import fiap.com.br.tds.cp.domainmodel.Carro;
import fiap.com.br.tds.cp.service.CarroService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carros")
@AllArgsConstructor
public class CarroController {

    @Autowired
    private CarroService service;

    @GetMapping("/")
    public ResponseEntity<List<Carro>> listAll() {

        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );

    }

    //http://localhost:8080/api/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Carro> findById( @PathVariable("id") Long id){
        System.out.println("http://localhost:8080/api/" + id);
        Carro emp = this.service.getById(id);
        if(emp == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEmployeeById(@PathVariable Long id ) {
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld(){
        return new ResponseEntity<String>("Hello World RESTFULL manner!!!", HttpStatus.OK);
    }

    @GetMapping("/hello2")
    public String helloWorld2(){
        return "Hello World RESTFULL manner22222222!!!";


    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> updateCarro(@PathVariable Long id, @RequestBody Carro carro){
        Carro databaseCarro = this.service.getById(id);
        databaseCarro.setMarca(carro.getMarca());
        databaseCarro.setAno(carro.getAno());
        databaseCarro.setPotencia(carro.getPotencia());
        databaseCarro.setEconomia(carro.getEconomia());
        databaseCarro.setTipo(carro.getTipo());
        databaseCarro.setPreco(carro.getPreco());
        this.service.update(databaseCarro);
        return new ResponseEntity<>(databaseCarro, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Carro> patchEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        Carro updatedEmployee = this.service.partialUpdate(id, updates);
        if( updatedEmployee != null ){
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}


