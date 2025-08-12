package Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import BD.Conexao;
import Class.Deposito;


public class DepositoDAO {

    ContaDAO contaDAO = new ContaDAO();
    
    public void Insert(Deposito deposito){
        String sql = "INSERT INTO depositos (conta_id, valor) VALUES (?, ?)";

        try{

        Connection con = Conexao.conectar();
        PreparedStatement pstmt = con.prepareStatement(sql);


            pstmt.setDouble(1, deposito.getContaId());
            pstmt.setDouble(2, deposito.getValor());

             int rowsAffected = pstmt.executeUpdate();

             if (rowsAffected > 0) {
                System.out.println("Deposito realizado com sucesso!");
            } else {
                System.out.println("Erro ao depositar.");
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao acessar banco de dados:");
            e.printStackTrace();
        
    }
    }  
    

    public void AtualizarSaldo(Double Deposito,Double SaldoAtual, String cliente_id){

        Double Atualizar = SaldoAtual + Deposito;
        

        String sql = "UPDATE contas SET saldo = ? WHERE cliente_id = ?";

        try{

        Connection con = Conexao.conectar();
        PreparedStatement pstmt = con.prepareStatement(sql);


            pstmt.setDouble(1, Atualizar);
            pstmt.setString(2, cliente_id);
           
            int linhasAfetadas = pstmt.executeUpdate();

        if (linhasAfetadas > 0) {
            System.out.println("Saldo atualizado com sucesso.");
        } else {
            System.out.println("Nenhuma conta foi atualizada. Verifique o cliente_id.");
        }

            
        }
        catch (SQLException e) {
            System.out.println("Erro ao acessar banco de dados:");
            e.printStackTrace();
        
    }


    }
}
