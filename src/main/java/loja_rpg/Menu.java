package loja_rpg;

import loja_rpg.model.Item;
import loja_rpg.model.Jogador;
import loja_rpg.util.BancoPopulador;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Scanner;
import loja_rpg.util.ConsultaService;

public class Menu {
    private Loja loja;
    private Jogador jogador;
    private Scanner scanner;
    private Session session; // Adiciona o atributo session

    // Construtor
    public Menu(Loja loja, Jogador jogador, Session session) {
        this.loja = loja;
        this.jogador = jogador;
        this.session = session;
        this.scanner = new Scanner(System.in);
    }

    // Método para exibir o menu
    public void exibirMenu() {
        int escolha;
        do {
            System.out.println("\nBem-vindo à Loja de Itens de RPG!");
            jogador.mostrarGold();
            System.out.println("1. Listar itens da loja");
            System.out.println("2. Adicionar um item à loja");
            System.out.println("3. Resetar loja para itens iniciais");
            System.out.println("4. Comprar item");
            System.out.println("5. Ver inventário");
            System.out.println("6. Consultas personalizadas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (escolha) {
                case 1:
                    loja.listarItens();
                    break;
                case 2:
                    loja.addItem();
                    break;
                case 3:
                    resetarLoja();
                    break;
                case 4:
                    comprarItem();
                    break;
                case 5:
                    jogador.listarInventario();
                    break;
                case 6:
                    consultasPersonalizadas();
                    break;
                case 0:
                    System.out.print("Tem certeza que deseja sair? (s/n): ");
                    String sair = scanner.next().toLowerCase();
                    if (sair.equals("s")) {
                        System.out.println("Saindo...");
                    } else {
                        escolha = -1; // volta pro menu
                    }
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (escolha != 0);
    }

    private void resetarLoja(){
        try {
            Transaction txReset = session.beginTransaction();
            BancoPopulador.resetarItensLoja(session);
            txReset.commit();
            // Atualiza a loja após o reset
            loja = BancoPopulador.popularBancoDeDados(session);
        } catch (Exception e) {
            System.out.println("Erro ao resetar itens da loja.");
            e.printStackTrace();
        }
    }
    private void consultasPersonalizadas() {
        ConsultaService consulta = new ConsultaService(session); // instância única
        int opcao;
        do {
            System.out.println("\nConsultas Personalizadas:");
            System.out.println("1. Buscar itens por nome");
            System.out.println("2. Buscar itens por faixa de preço");
            System.out.println("3. Mostrar itens consumíveis");
            System.out.println("4. Mostrar itens no inventário de um jogador");
            System.out.println("5. Mostrar jogadores que possuem poções");
            System.out.println("6. Mostrar total de itens cadastrados");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("Digite parte do nome do item: ");
                    String nome = scanner.nextLine();
                    consulta.buscarItemPorNomeParcial(nome);
                    break;
                case 2:
                    System.out.print("Digite o preço mínimo: ");
                    int min = scanner.nextInt();
                    System.out.print("Digite o preço máximo: ");
                    int max = scanner.nextInt();
                    consulta.buscarItensPorFaixaDePreco(min, max);
                    break;
                case 3:
                    // Você pode implementar isso com uma consulta por tipo
                    consulta.buscarItemPorNomeParcial("Poção"); // exemplo temporário
                    break;
                case 4:
                    System.out.print("Digite o nome do jogador: ");
                    String nomeJogador = scanner.nextLine();

                    // Aqui precisa buscar o jogador por nome antes
                    Jogador jogadorConsultado = session.createQuery(
                                    "FROM Jogador j WHERE LOWER(j.nome) = LOWER(:nome)", Jogador.class)
                            .setParameter("nome", nomeJogador)
                            .uniqueResult();

                    if (jogadorConsultado != null) {
                        consulta.buscarItensDoJogador(jogadorConsultado);
                    } else {
                        System.out.println("Jogador não encontrado.");
                    }
                    break;
                case 5:
                    consulta.buscarJogadoresComPocoes();
                    break;
                case 6:
                    consulta.contarItensNaLoja();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // Método para comprar um item
    private void comprarItem() {
        System.out.print("Digite o código do item que deseja comprar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        Item item = loja.buscarItem(codigo);
        if (item != null) {
            if (jogador.verificarGold(item.getPreco())) {
                jogador.adicionarItem(item);
                System.out.println(item.getNome() + " foi comprado e adicionado ao inventário!");
            } else {
                System.out.println("Você não tem gold suficiente para comprar " + item.getNome() + ".");
            }
        } else {
            System.out.println("Item não encontrado!");
        }
    }
}
