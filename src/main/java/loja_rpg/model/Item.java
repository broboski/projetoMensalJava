package loja_rpg.model;

import jakarta.persistence.*;

@Entity //mapeia a classe para uma tabela no banco de dados.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Todas as subclasses de item serão armazenadas em uma única tabela
@DiscriminatorColumn(name = "classe_item") // Define a coluna usada para diferenciar os tipos de item
public abstract class Item {
    @Id // identificador único gerado automaticamente pelo banco.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int codigo;
    private String nome;
    private int preco;
    @Enumerated(EnumType.STRING)
    private TipoItem tipo; // enum

    // Construtores
    public Item() {} // Construtor padrão

    public Item(int codigo, String nome, int preco, TipoItem tipo) { // Construtor parametrizado
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
    }

    public Item(Item outroItem) { // Construtor de cópia
        this.codigo = outroItem.codigo;
        this.nome = outroItem.nome;
        this.preco = outroItem.preco;
        this.tipo = outroItem.tipo;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public int getPreco() { return preco; }
    public TipoItem getTipo() { return tipo; }

    public void setPreco(int preco) { this.preco = preco; }

    // Método abstrato para uso do item
    public abstract void usar();

    @Override
    public String toString() {
        return "Item [Código: " + codigo + ", Nome: " + nome + ", Preço: " + preco + ", Classe: " + tipo;
    }
}












