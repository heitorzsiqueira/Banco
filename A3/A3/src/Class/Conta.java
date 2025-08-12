package Class;
import Query.LoginDAO;

public class Conta {

    private String clienteId;
    String numero;
    private String numeroConta;
    private double saldo;
    LoginDAO loginDAO = new LoginDAO();


    public Conta(String clienteID, double saldo) {
        this.clienteId = loginDAO.RetornaId(clienteID) ;
        int idInt = Integer.parseInt(clienteId);
        this.numero = String.format("%08d", idInt);
        this.numeroConta = this.numero;
        this.saldo = saldo;
      
    }

    public String getClienteId() {
        return clienteId;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

}
