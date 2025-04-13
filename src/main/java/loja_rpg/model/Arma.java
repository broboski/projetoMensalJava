package loja_rpg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("ARMA")
public class Arma extends Item {
    private String tipoArma; // Tipo da arma, como "Espada", "Arco", etc.
    private int dano;

    public Arma() {
        // Construtor vazio obrigatório para o JPA
    }

    // Construtor da classe Armas, que chama o construtor da classe pai (Item)
    public Arma(int codigo, String nome, String tipoArma, int preco, TipoItem tipoItem , int dano) {
        super(codigo, nome, preco, tipoItem); // Chama o construtor da classe pai (Item)
        this.tipoArma = tipoArma; // Define o tipoArma da arma
        this.dano = dano; // Define o dano da arma
    }

    // Getter para o tipoArma da arma
    public String getTipoArma() {
        return tipoArma;
    }
    public int getDano() {
        return dano;
    }

    @Override
    public void usar() {
        System.out.println("Você usou a arma: " + getNome() + " causando " + dano + " de dano!");
    }

    // Sobrescreve o método toString() para incluir o tipoArma da arma e o dano
    @Override
    public String toString() {
        return super.toString() + ", Tipo: " + tipoArma + ", Dano: " + dano + "]";
    }
}



