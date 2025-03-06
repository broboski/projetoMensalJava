public class Itens {
    int preco;
    float forca;
    float magia;
    float resistencia;
    float dano;

    public Itens(int preco, float forca, float magia, float resistencia, float dano){
        this.preco = preco;
        this.forca = forca;
        this.magia = magia;
        this.resistencia = resistencia;
        this.dano = dano;
    }
    public void mostrarInfo(){
        System.out.println("Preço: " + preco);
        System.out.println("Força: " + forca);
        System.out.println("Magia: " + magia);
        System.out.println("Resistencia: " + resistencia);
        System.out.println("Dano: " + dano);
    }
}