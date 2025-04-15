package loja_rpg.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    static { // Abre o arquivo config.properties e carrega as chaves e valores do arquivo
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo config.properties não encontrado em resources!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar config.properties: " + e.getMessage(), e);
        }
    }

    // Permite acessar uma chave individual
    public static String get(String key) {
        return properties.getProperty(key);
    }

    // Permite carregar todas as propriedades (ex: para passar para o Hibernate)
    public static Properties loadProperties() {
        return properties;
    }
}
