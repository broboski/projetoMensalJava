package loja_rpg.util;

import loja_rpg.Loja;
import loja_rpg.model.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class BancoPopulador {

    public static Loja popularBancoDeDados(Session session) {
        // Verifica se já existem itens no banco
        Query<Item> query = session.createQuery("FROM Item", Item.class);
        List<Item> itensExistentes = query.getResultList();

        Loja loja = new Loja();

        if (itensExistentes.isEmpty()) {
            // Adiciona jogador e os itens iniciais apenas se o banco estiver vazio
            Jogador jogador = new Jogador("Herói");
            session.persist(jogador);

            loja.adicionarItem(new Arma(1, "Espada de Ferro", "Espada", 50, TipoItem.ARMA, 10));
            loja.adicionarItem(new Armadura(2, "Armadura de Couro", 30, 5));
            loja.adicionarItem(new Pocao(3, "Poção de Cura", 20, 15));

            for (Item item : loja.getItens()) {
                session.persist(item);
            }

            System.out.println("Banco populado com dados de exemplo.");
        } else {
            // Apenas carrega os itens existentes para a loja
            loja.getItens().addAll(itensExistentes);
            System.out.println("Itens já existentes carregados do banco.");
        }

        return loja;
    }

    public static void resetarItensLoja(Session session) {
        // Remove todos os itens existentes
        session.createQuery("DELETE FROM Item").executeUpdate();

        Loja loja = new Loja();
        loja.adicionarItem(new Arma(1, "Espada de Ferro", "Espada", 50, TipoItem.ARMA, 10));
        loja.adicionarItem(new Armadura(2, "Armadura de Couro", 30, 5));
        loja.adicionarItem(new Pocao(3, "Poção de Cura", 20, 15));

        // Use merge() ao invés de persist()
        for (Item item : loja.getItens()) {
            session.merge(item);  // <-- Aqui
        }

        System.out.println("Loja resetada com os 3 itens iniciais.");
    }
}
