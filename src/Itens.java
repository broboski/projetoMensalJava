public class Itens {
    float preco;
    float forca;
    float magia;
    float resistencia;
    int dano;

    public Itens(float preco, float forca, float magia, float resistencia, int dano){
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