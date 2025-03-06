import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Armas espadaSimples = new Armas("Espada simples", 6, 5, 0, 3, 5);
        Armas cajadoMagico = new Armas("Cajado mágico", 8, 1, 5, 1, 6);
        Armas adagas = new Armas("Adaga simples", 4, 4, 0, 2, 4);

        espadaSimples.mostrarInfo();
        cajadoMagico.mostrarInfo();
        adagas.mostrarInfo();


    /*int escolha;
    do{
        System.out.println("Bem vindo. Selecione uma opção a seguir: \n0.Encerrar o programa \n1.Montar build \n2.Inspecionar build");
        escolha = scanner.nextInt();
        switch(escolha){
            case 1 -> buildNova();
            case 2 -> buildSalva();
        }


    }while(escolha != 0);
*/

        scanner.close();
    }
}
