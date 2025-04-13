package loja_rpg.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int gold;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> inventario = new ArrayList<>();

    // Construtor padrão exigido pelo JPA
    public Jogador() {}

    // Construtor
    public Jogador(String nome) {
        this.nome = nome;
        this.gold = 90; // Quantidade inicial de gold
    }

    public void adicionarItem(Item item) {
        inventario.add(item);
        System.out.println(item.getNome() + " foi adicionado ao inventário.");
    }

    public void listarInventario() {
        System.out.println("Inventário de " + nome + ":");
        if (inventario.isEmpty()) {
            System.out.println("Você não comprou nenhum item ainda.");
        } else {
            for (Item item : inventario) {
                System.out.println(item);
            }
        }
    }

    public boolean verificarGold(int preco) {
        if (this.gold >= preco) {
            this.gold -= preco;
            System.out.println("Item comprado. Quantidade atual de gold: " + this.gold);
            return true;
        } else {
            System.out.println("Gold insuficiente. Você possui " + this.gold + ", e este item custa " + preco);
            return false;
        }
    }

    public void mostrarGold() {
        System.out.printf("Você possui %d moedas!\n", gold);
    }

    public int getGold() {
        return this.gold;
    }

    // Getters e Setters necessários pro JPA
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Item> getInventario() {
        return inventario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setInventario(List<Item> inventario) {
        this.inventario = inventario;
    }
}
