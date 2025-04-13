# 🛡️ Loja RPG - Aplicação Console em Java

Sistema de gerenciamento de loja RPG com operações de CRUD via console. Desenvolvido em Java utilizando Maven, JPA (Hibernate) e PostgreSQL.

---

## ✅ Requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- PostgreSQL instalado e em execução
- IDE (opcional): IntelliJ IDEA, Eclipse ou VS Code

---

## ⚙️ Configuração do Banco de Dados

1. **Criar o banco de dados** no PostgreSQL:

```sql
CREATE DATABASE loja_rpg;
```

2. **Editar o arquivo `config.properties`** (na raiz ou em `src/main/resources`) com os dados da sua conexão:

```
hibernate.connection.url=jdbc:postgresql://localhost:5432/loja_rpg
hibernate.connection.username=seu_usuario
hibernate.connection.password=sua_senha
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.hbm2ddl.auto=update
hibernate.show_sql=true
hibernate.format_sql=true
```

---

## 🛠️ Como Executar

### Via terminal

```bash
# Compilar o projeto
mvn clean package

# Executar a aplicação
mvn exec:java -Dexec.mainClass="loja_rpg.Main"
```

> 🏁 Classe principal: `loja_rpg.Main`

### Ou via IDE

- Importe o projeto como um projeto Maven
- Configure o SDK para Java 17
- Rode a classe `loja_rpg.Main`

---

## 💡 Funcionalidades

- Menu interativo via console
- Persistência de dados com Hibernate/JPA
- CRUD completo de jogadores e itens (armas, armaduras e poções)
- Inventário de jogador
- População automática com dados de exemplo
- Consultas com:
    - `findAll`, `findBy...`
    - JPQL com JOIN
    - Filtros por intervalo (ex: preço)
    - Busca com `LIKE`
    - Agregações como `SUM`, `AVG`

---

## 🧪 Tecnologias Utilizadas

- Java 17
- Maven
- JPA (Hibernate)
- PostgreSQL
- JDBC Driver: `org.postgresql:postgresql`

---

## 📝 Licença

Projeto desenvolvido para fins educacionais.