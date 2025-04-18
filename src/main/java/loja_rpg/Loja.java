package loja_rpg;

import loja_rpg.model.*;
import loja_rpg.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Loja {
    private List<Item> itens;
    Scanner scanner = new Scanner(System.in);

    public Loja() {
        this.itens = new ArrayList<>();
    }

    // Adiciona item à lista e ao banco de dados
    public void adicionarItem(Item item) {
        itens.add(item);
        salvarNoBanco(item); // salva no banco de dados
        System.out.println(item.getNome() + " foi adicionado à loja.");
    }

    // Gera código único para novos itens
    public static int gerarCodigoUnico(List<Item> itens) {
        int codigo = 1;
        while (codigoEmUso(codigo, itens)) {
            codigo++;
        }
        return codigo;
    }

    private static boolean codigoEmUso(int codigo, List<Item> itens) {
        for (Item item : itens) {
            if (item.getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    // Salva o item no banco usando Hibernate
    private void salvarNoBanco(Item item) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erro ao salvar item no banco: " + e.getMessage());
        }
    }

    // Adiciona item via terminal
    public void addItem() {
        try {
            System.out.println("Deseja adicionar seu item à loja?\n1. Sim\n2. Não");
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Limpa quebra de linha pendente

            if (escolha == 1) {
                System.out.println("Digite o nome do item a ser adicionado: ");
                String nome = scanner.nextLine();

                System.out.println("Escolha a classe do item a ser adicionado:");
                System.out.println("1. Arma\n2. Armadura\n3. Poção");
                int tipoItemEscolha = scanner.nextInt();
                scanner.nextLine(); // Limpa quebra de linha

                System.out.println("Digite o preço do item: ");
                int preco = scanner.nextInt();
                scanner.nextLine(); // Limpa quebra de linha

                switch (tipoItemEscolha) {
                    case 1: // Arma
                        List<String> tiposValidos = List.of("Espada", "Arco", "Machado", "Adaga", "Cajado");
                        String tipoArma = "";

                        boolean tipoValido = false;
                        while (!tipoValido) {
                            System.out.println("Digite o tipo da arma (Espada, Arco, Machado, Adaga, Cajado): ");
                            tipoArma = scanner.nextLine().trim();

                            for (String tipo : tiposValidos) {
                                if (tipo.equalsIgnoreCase(tipoArma)) {
                                    tipoArma = tipo; // usa a versão correta capitalizada
                                    tipoValido = true;
                                    break;
                                }
                            }

                            if (!tipoValido) {
                                System.out.println("Tipo inválido. Tente novamente.");
                            }
                        }

                        System.out.println("Digite o dano da arma: ");
                        int dano = scanner.nextInt();
                        scanner.nextLine(); // limpa quebra de linha

                        Arma novaArma = new Arma(gerarCodigoUnico(itens), nome, tipoArma, preco, TipoItem.ARMA, dano);
                        adicionarItem(novaArma);
                        break;

                    case 2: // Armadura
                        System.out.println("Digite o valor de defesa da armadura: ");
                        int defesa = scanner.nextInt();
                        scanner.nextLine(); // Limpa quebra de linha

                        Armadura novaArmadura = new Armadura(gerarCodigoUnico(itens), nome, preco, defesa);
                        adicionarItem(novaArmadura);
                        break;

                    case 3: // Poção
                        System.out.println("Digite o valor de cura da poção: ");
                        int cura = scanner.nextInt();
                        scanner.nextLine(); // Limpa quebra de linha

                        Pocao novaPocao = new Pocao(gerarCodigoUnico(itens), nome, preco, cura);
                        adicionarItem(novaPocao);
                        break;

                    default:
                        System.out.println("Escolha uma opção válida!");
                        break;
                }

            } else if (escolha == 2) {
                System.out.println("Voltando ao menu...");
            } else {
                System.out.println("Escolha uma opção válida.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Digite apenas números onde solicitado.");
            scanner.nextLine(); // Limpa o buffer
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao adicionar o item.");
            e.printStackTrace();
        }
    }

    public void modificarItem() {
        if (itens.isEmpty()) {
            System.out.println("A loja não tem itens disponíveis.");
            return;
        }

        listarItens();
        System.out.print("Digite o código do item que deseja modificar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        Item itemEncontrado = buscarItem(codigo);

        if (itemEncontrado == null) {
            System.out.println("Item não encontrado!");
            return;
        }

        System.out.println("Modificando o item: " + itemEncontrado.getNome());
        System.out.print("Novo nome (deixe em branco para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isEmpty()) {
            itemEncontrado.setNome(novoNome);
        }

        System.out.print("Novo preço (ou 0 para manter): ");
        int novoPreco = scanner.nextInt();
        scanner.nextLine();
        if (novoPreco > 0) {
            itemEncontrado.setPreco(novoPreco);
        }

        // Atualiza no banco
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(itemEncontrado); // atualiza o item
            tx.commit();
            System.out.println("Item modificado com sucesso no banco!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar item no banco: " + e.getMessage());
        }
    }
    public void removerItem() {
        if (itens.isEmpty()) {
            System.out.println("A loja não tem itens disponíveis.");
            return;
        }

        listarItens();
        System.out.print("Digite o código do item que deseja remover: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        Item itemEncontrado = buscarItem(codigo);

        if (itemEncontrado == null) {
            System.out.println("Item não encontrado!");
            return;
        }

        // Remove da lista
        itens.remove(itemEncontrado);

        // Remove do banco
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Busca o item pelo campo "codigo", não pelo ID
            Item itemParaDeletar = session
                    .createQuery("FROM Item WHERE codigo = :codigo", Item.class)
                    .setParameter("codigo", itemEncontrado.getCodigo())
                    .uniqueResult();

            if (itemParaDeletar != null) {
                session.remove(itemParaDeletar);
                tx.commit();
                System.out.println("Item removido com sucesso do banco!");
            } else {
                System.out.println("Item não encontrado no banco.");
                tx.rollback();
            }
        } catch (Exception e) {
            System.err.println("Erro ao remover item do banco: " + e.getMessage());
        }

    }


    // Lista os itens em memória
    public void listarItens() {
        System.out.println("Itens disponíveis na loja:");
        for (Item item : itens) {
            System.out.println(item);
        }
    }

    // Busca item pelo código na lista em memória
    public Item buscarItem(int codigo) {
        for (Item item : itens) {
            if (item.getCodigo() == codigo) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getItens() {
        return itens;
    }
}
