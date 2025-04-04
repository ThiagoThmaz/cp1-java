package fiap.com.br.tds.cp.domainmodel;


import java.util.Objects;

public class Carro {

    private Long id;
    private String marca;
    private int ano;
    private int potencia;
    private int economia;
    private String tipo;
    private double preco;

    public Carro(Long id, String marca, int ano, int potencia, int economia, String tipo, Double preco) {
        this.id = id;
        this.marca = marca;
        this.ano = ano;
        this.potencia = potencia;
        this.economia = economia;
        this.tipo = tipo;
        this.preco = preco;
    }

    public Carro() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }


    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public int getEconomia() {
        return economia;
    }

    public void setEconomia(int economia) {
        this.economia = economia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Carro carro = (Carro) o;
        return Objects.equals(id, carro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Carro{" +
                "marca='" + marca + '\'' +
                ", ano=" + ano +
                ", potencia=" + potencia +
                ", economia=" + economia +
                ", tipo='" + tipo + '\'' +
                ", preco=" + preco +
                ", id=" + id +
                '}';
    }
}
