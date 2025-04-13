package loja_rpg;

import loja_rpg.model.*;
import loja_rpg.util.BancoPopulador;
import loja_rpg.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Loja loja = BancoPopulador.popularBancoDeDados(session);
            Jogador jogador = session.createQuery("FROM Jogador", Jogador.class).setMaxResults(1).uniqueResult();

            tx.commit();

            Menu menu = new Menu(loja, jogador, session);
            menu.exibirMenu();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }
}
