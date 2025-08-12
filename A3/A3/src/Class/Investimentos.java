package Class;


public class Investimentos {

    private int contaId;
    private int tipoId;
    private double valor;
    private double rendimentoEsperado;

    // Construtores
    public Investimentos(String contaId, int tipoId, double valor, double rendimentoEsperado) {

        double prepare = Double.parseDouble(contaId);

        this.contaId = (int) prepare;
        this.tipoId = tipoId;
        this.valor = valor;
        this.rendimentoEsperado = rendimentoEsperado;
    }

    
    public int getContaId(){
        return contaId;
    }

    public void setContaId(int contaId) {
        this.contaId = contaId;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    public double getRendimentoEsperado() {
        return rendimentoEsperado;
    }

    public void setRendimentoEsperado(double rendimentoEsperado) {
        this.rendimentoEsperado = rendimentoEsperado;
    }

    
    
}
