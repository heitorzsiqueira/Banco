package Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import BD.Conexao;
import Class.Conta;
import Class.Login;
import java.time.LocalDate;



public class ContaDAO {

 LoginDAO loginDAO = new LoginDAO();
 String saldo ;
 String id;
 double SaldoAtual;

        public void criarConta(Conta conta) {
        String sql = "INSERT INTO contas (cliente_id, numero_conta, saldo) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conta.getClienteId());
            stmt.setString(2, conta.getNumeroConta());
            stmt.setDouble(3, conta.getSaldo());
            
            stmt.executeUpdate();
            System.out.println("Conta bancária criada com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar conta bancária:");
            e.printStackTrace();
        }
        
    }

    public String MostraSaldo(String token, boolean Desconectar){
        
        String sql = "SELECT saldo FROM contas WHERE cliente_id = ?";
        
        if (Desconectar == false) {
            try (Connection conn = Conexao.conectar();
                    PreparedStatement stmt = conn.prepareStatement(sql)){

                    stmt.setString(1, token);

                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {

                        saldo = Double.toString(rs.getDouble("saldo"));
                        
                    }

                }catch (SQLException e) {
                    System.out.println("Erro ao criar conta bancária:");
                    e.printStackTrace();
                }
                return saldo;
        }else{
            saldo = "Sem saldo";
            return saldo;
        }

            
    }
        


    public void Desconectar(){
        saldo = "Sem saldo";
      
    }

    public String RetornaId(String cliente_id){
        String sql = "SELECT id FROM contas WHERE cliente_id = ?";



         try (Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1, cliente_id);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {

                    id = Double.toString(rs.getDouble("id"));
                    
                }

            }catch (SQLException e) {
                System.out.println("Erro ao criar conta bancária:");
                e.printStackTrace();
            }
            return id;
    }

    public double RetornaSaldo(String cliente_id){
        String sql="SELECT saldo FROM contas WHERE cliente_id = ?";

        try (Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1, cliente_id);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {

                    SaldoAtual = rs.getDouble("saldo");
                    
                }

            }catch (SQLException e) {
                System.out.println("Erro ao criar conta bancária:");
                e.printStackTrace();
            }
            return SaldoAtual;
    }


    public boolean ExisteConta(String cliente_id){
        String sql = "SELECT id FROM contas WHERE cliente_id = ?";
        boolean Existe = false;
        try{
            Connection con = Conexao.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
           

            statement.setString(1, cliente_id);
            

            ResultSet rs = statement.executeQuery();
           
          
            
            while (rs.next()) {

                Existe = true;

                String id = rs.getString("id");

            }
        

        }
        catch (SQLException e) {
            System.out.println("Erro ao acessar banco de dados:");
            e.printStackTrace();
        
    }

        return Existe;
    }

    public void Extrato(String cliente_id){
        String sql = "SELECT 'Depósito' AS tipo, d.id AS transacao_id, d.valor, d.data_deposito AS data FROM depositos d WHERE d.conta_id = ? UNION ALL SELECT 'Saque' AS tipo, s.id AS transacao_id, s.valor, s.data_saque AS data FROM saques s WHERE s.conta_id = ? ORDER BY data ASC" ;


        try{
            Connection con = Conexao.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
           

            statement.setString(1, cliente_id);
            statement.setString(2, cliente_id);
            

            ResultSet rs = statement.executeQuery();
           
          
            
            while (rs.next()) {

             

                String tipo = rs.getString("tipo");
                String transacao_id = rs.getString("transacao_id");
                String valor = rs.getString("valor");
                String data = rs.getString("data");

                System.out.println("Tipo: "+tipo+"/ ID Transação: "+transacao_id+"/ Valor: "+ valor+"/ Data: "+data +"\n");

            }
        

        }
        catch (SQLException e) {
            System.out.println("Erro ao acessar banco de dados:");
            e.printStackTrace();
        
    }

    }
    
    
}
