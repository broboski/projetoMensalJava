package loja_rpg.util;

import jakarta.persistence.Query;
import loja_rpg.model.Item;
import loja_rpg.model.Jogador;
import org.hibernate.Session;

import java.util.List;

public class ConsultaService {

    private final Session session;

    public ConsultaService(Session session) {
        this.session = session;
    }

    // 1. Consulta com LIKE (%): busca itens com nome parcialmente correspondente
    public void buscarItemPorNomeParcial(String nomeParcial) {
        String jpql = "FROM Item i WHERE LOWER(i.nome) LIKE LOWER(:nome)";
        List<Item> itens = session.createQuery(jpql, Item.class)
                .setParameter("nome", "%" + nomeParcial + "%")
                .getResultList();

        System.out.println("Itens encontrados com nome parecido com '" + nomeParcial + "':");
        itens.forEach(System.out::println);
    }

    // 2. Consulta com intervalo: preço entre valores
    public void buscarItensPorFaixaDePreco(int min, int max) {
        String jpql = "FROM Item i WHERE i.preco BETWEEN :min AND :max";
        List<Item> itens = session.createQuery(jpql, Item.class)
                .setParameter("min", min)
                .setParameter("max", max)
                .getResultList();

        System.out.println("Itens com preço entre " + min + " e " + max + ":");
        itens.forEach(System.out::println);
    }

    // 3. Consulta com JOIN: busca todos os itens do inventário de um jogador
    public void buscarItensDoJogador(Jogador jogador) {
        String jpql = "SELECT i FROM Jogador j JOIN j.inventario i WHERE j.id = :id";
        List<Item> itens = session.createQuery(jpql, Item.class)
                .setParameter("id", jogador.getId())
                .getResultList();

        System.out.println("Itens no inventário do jogador " + jogador.getNome() + ":");
        itens.forEach(System.out::println);
    }

    // 4. Outra consulta com JOIN: jogadores que possuem itens do tipo 'Poção'
    public void buscarJogadoresComPocoes() {
        String jpql = "SELECT DISTINCT j FROM Jogador j JOIN j.inventario i WHERE i.tipo = 'POCAO'";
        List<Jogador> jogadores = session.createQuery(jpql, Jogador.class).getResultList();

        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador possui poções.");
        } else {
            System.out.println("Jogadores que possuem poções:");
            jogadores.forEach(j -> System.out.println(j.getNome()));
        }
    }

    // 5. Consulta agregada: total de itens na loja
    public void contarItensNaLoja() {
        String jpql = "SELECT COUNT(i) FROM Item i";
        Long total = session.createQuery(jpql, Long.class).getSingleResult();

        System.out.println("Total de itens na loja: " + total);
    }
    // 6. Itens consumíveis (ex: POCAO)
    public void buscarItensConsumiveis() {
        String jpql = "FROM Item i WHERE i.tipo = 'POCAO'";
        List<Item> itens = session.createQuery(jpql, Item.class).getResultList();

        System.out.println("Itens consumíveis (POÇÕES):");
        itens.forEach(System.out::println);
    }
    // 7. Por jogador
    public void buscarItensDoJogador(String nomeJogador) {
        String jpqlJogador = "FROM Jogador j WHERE j.nome = :nome";
        Jogador jogador = session.createQuery(jpqlJogador, Jogador.class)
                .setParameter("nome", nomeJogador)
                .getSingleResult();

        String jpql = "SELECT i FROM Jogador j JOIN j.inventario i WHERE j.id = :id";
        List<Item> itens = session.createQuery(jpql, Item.class)
                .setParameter("id", jogador.getId())
                .getResultList();

        System.out.println("Itens no inventário do jogador " + jogador.getNome() + ":");
        itens.forEach(System.out::println);
    }

}

