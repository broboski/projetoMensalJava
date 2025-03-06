public class Armas extends Itens {
    // Atributos específicos de Armas
    String tipoArma;

    // Construtor da classe Armas
    public Armas(String tipoArma, int preco, float forca, float magia, float resistencia, float dano) {
        super(preco, forca, magia, resistencia, dano);
        this.tipoArma = tipoArma;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Arma: " + tipoArma);
        super.mostrarInfo();
    }
}