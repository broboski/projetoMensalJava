package loja_rpg.util;

import loja_rpg.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Carrega as propriedades externas
            Properties props = ConfigLoader.loadProperties();

            Configuration configuration = new Configuration();
            configuration.setProperties(props);

            // Adiciona as classes anotadas
            configuration.addAnnotatedClass(Jogador.class);
            configuration.addAnnotatedClass(Item.class);
            configuration.addAnnotatedClass(Arma.class);
            configuration.addAnnotatedClass(Armadura.class);
            configuration.addAnnotatedClass(Pocao.class);

            return configuration.buildSessionFactory();
        }catch (Exception ex) {
            throw new RuntimeException("Erro ao construir SessionFactory", ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
