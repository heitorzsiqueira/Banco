package Class;


public class Deposito {
   
    private double contaId;
    private double valor;
   

    // Construtor sem ID (usado para inserir no banco)
    public Deposito(double contaId, double valor) {
        this.contaId = contaId;
        this.valor = valor;
       
    }

    // Getters
    public double getContaId() {
        return contaId;
    }

    public double getValor() {
        return valor;
    }

    
    
}
