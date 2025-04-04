package fiap.com.br.tds.cp.datasource.repositories;

import fiap.com.br.tds.cp.domainmodel.Carro;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Component
public class CarroMockRepository {

    private final List<Carro> dataset = new ArrayList<>();

    public CarroMockRepository(){
        this.dataset.add( new Carro(1L, "SIENA" ,2023,200,400,"combustão",15.000));
        this.dataset.add( new Carro(2L, "SEDAN",2022,600,300,"híbrido",19.000));
        this.dataset.add( new Carro(3L, "HONDA",2024,900,700,"elétrico",150.000));
        this.dataset.add( new Carro(4L, "SIENA",2025,800,800,"combustão",150.000));
        this.dataset.add( new Carro(5L, "FIAT",2015,700,900,"elétrico",190.000));
        this.dataset.add( new Carro(6L, "CROSS",2005,670,700,"híbrido",450.000));
        this.dataset.add( new Carro(7L, "BMW",2014,540,700,"elétrico",630.000));
        this.dataset.add( new Carro(8L, "MERCEDEZ",2013,200,400,"combustão",150.000));
        this.dataset.add( new Carro(9L, "NISSAN",2016,150,670,"híbrido",250.000));
        this.dataset.add( new Carro(10L, "HYUDAY",2004,800,400,"elétrico",150.000));
        this.dataset.add( new Carro(11L, "FERRARI",2009,890,800,"combustão",350.000));
        this.dataset.add( new Carro(12L, "FIAT",2008,450,500,"elétrico",140.000));
        this.dataset.add( new Carro(13L, "HONDA",2009,190,700,"híbrido",350.000));


    }

    public List<Carro> getAll(){ return this.dataset.subList(0,this.dataset.size());}

    public Carro save(Carro carro) {
        this.dataset.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        Long lastID = this.dataset.get(this.dataset.size() -1).getId();
        carro.setId(lastID + 1);
        this.dataset.add(carro);
        return carro;
    }

    public void deleteById(Long id) {
        this.dataset.removeIf(
                carro ->
                        carro.getId().equals(id));
    }

    public void delete(Carro anCarro) {
        this.dataset.removeIf(
                employee ->
                        employee.getId().equals(anCarro.getId()));
    }

    public Carro findById(Long id) {
        for(Carro carro : this.getAll()){
            if(carro.getId().equals(id)){
                return carro;
            }
        }
        return null;
    }

}
