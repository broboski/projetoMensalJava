package loja_rpg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity //mapeia a classe para uma tabela no banco de dados.
@DiscriminatorValue("ARMADURA") //herança com uma única tabela
public class Armadura extends Item {
    private int defesa;

    // Construtor padrão obrigatório para o JPA
    public Armadura() {}

    // Construtor com parâmetros
    public Armadura(int codigo, String nome, int preco, int defesa) {
        super(codigo, nome, preco, TipoItem.ARMADURA);
        this.defesa = defesa;
    }

    public Armadura(Armadura outraArmadura) { // Construtor de cópia
        super(outraArmadura);
        this.defesa = outraArmadura.defesa;
    }

    public int getDefesa() {
        return defesa;
    }

    // Método abstrato sobrescrito
    @Override
    public void usar() {
        System.out.println("Você equipou " + getNome() + " e ganhou " + defesa + " de defesa!");
    }

    @Override
    public String toString() {
        return super.toString() + ", Defesa: " + defesa + "]";
    }
}






