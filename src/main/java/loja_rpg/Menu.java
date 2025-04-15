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
    private Session session; // Sessão do Hibernate para transações com o banco de dados

    // Construtor
    public Menu(Loja loja, Jogador jogador, Session session) {
        this.loja = loja;
        this.jogador = jogador;
        this.session = session;
        this.scanner = new Scanner(System.in);
    }

    // Exibe o menu principal e trata a interação com o jogador.
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
                    loja.listarItens(); // Lista todos os itens disponíveis na loja
                    break;
                case 2:
                    loja.addItem(); // Adiciona um novo item à loja via terminal
                    break;
                case 3:
                    resetarLoja(); // Restaura os itens iniciais da loja
                    break;
                case 4:
                    comprarItem(); // Permite o jogador comprar um item da loja
                    break;
                case 5:
                    jogador.listarInventario(); // Mostra os itens que o jogador possui
                    break;
                case 6:
                    consultasPersonalizadas(); // Executa consultas específicas no banco
                    break;
                case 0:
                    System.out.print("Tem certeza que deseja sair? (s/n): ");
                    String sair = scanner.next().toLowerCase();
                    if (sair.equals("s")) {
                        System.out.println("Saindo...");
                    } else {
                        escolha = -1; // Cancela a saída e volta ao menu
                    }
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (escolha != 0);
    }

    private void resetarLoja(){ // Reseta os itens da loja para os itens padrões definidos no banco.
        try {
            Transaction txReset = session.beginTransaction(); // Inicia uma transação
            BancoPopulador.resetarItensLoja(session); // Limpa os itens atuais da loja
            txReset.commit(); // Confirma a transação
            loja = BancoPopulador.popularBancoDeDados(session); // Recarrega a loja com os itens iniciais
        } catch (Exception e) {
            System.out.println("Erro ao resetar itens da loja.");
            e.printStackTrace();
        }
    }
    private void consultasPersonalizadas() { // Menu de consultas personalizadas no banco de dados usando HQL.
        ConsultaService consulta = new ConsultaService(session);
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
                case 1: // Busca de itens por nome parcial
                    System.out.print("Digite parte do nome do item: ");
                    String nome = scanner.nextLine();
                    consulta.buscarItemPorNomeParcial(nome);
                    break;
                case 2: // Busca de itens por faixa de preço
                    System.out.print("Digite o preço mínimo: ");
                    int min = scanner.nextInt();
                    System.out.print("Digite o preço máximo: ");
                    int max = scanner.nextInt();
                    consulta.buscarItensPorFaixaDePreco(min, max);
                    break;
                case 3: // Itens consumíveis (poções)
                    consulta.buscarItemPorNomeParcial("Poção");
                    break;
                case 4: // Consulta o jogador no banco com nome exato (ignorando maiúsculas/minúsculas)
                    System.out.print("Digite o nome do jogador: ");
                    String nomeJogador = scanner.nextLine();

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
                case 5: // Mostra jogadores que têm poções no inventário
                    consulta.buscarJogadoresComPocoes();
                    break;
                case 6: // Conta todos os itens cadastrados
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

    // Permite o jogador comprar um item da loja
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
