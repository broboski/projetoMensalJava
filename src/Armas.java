public class Armas extends Itens {
    // Atributos específicos de Armas
    String tipoArma;

    // Construtor da classe Armas
    public Armas(String tipoArma, float preco, float forca, float magia, float resistencia, int dano) {
        super(preco, forca, magia, resistencia, dano);
        this.tipoArma = tipoArma;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Arma: " + tipoArma);
        super.mostrarInfo();
    }
}