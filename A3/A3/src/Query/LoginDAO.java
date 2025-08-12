package Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Class.Login;
import BD.Conexao;

public class LoginDAO {

  String cpf_token;
  boolean retornou =  false;
  private String id_show;


        public void select(Login login){
        String sql = "SELECT nome,senha,cpf FROM clientes WHERE (cpf = ? && senha = ?)";
        try{
            Connection con = Conexao.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
           

            statement.setString(1, login.getCpf());
            statement.setString(2, login.getSenha());

            ResultSet rs = statement.executeQuery();
           
          
            
            while (rs.next()) {

                retornou = true;

                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");

                System.out.println("Bem-vindo! " + nome);
                
                cpf_token = cpf;
                
            
            }
            if (retornou == false) {
                System.out.println("Não foi possível fazer login. Verifique seu CPF e senha e tente novamente.");
            }

        }
        catch (SQLException e) {
            System.out.println("Erro ao acessar banco de dados:");
            e.printStackTrace();
        
    }
    }

    public String MostraToken(){
                if(retornou == true){
                    return cpf_token;
                }else{
                    cpf_token = "Sem login";
                    return cpf_token;
                }
            }
    
    public String DesconectarToken(){
        cpf_token = "Sem login";
        return cpf_token;
    }

    public String RetornaId(String cpf) {
    String sql = "SELECT id FROM clientes WHERE cpf = ?";
    

    try (Connection con = Conexao.conectar();
         PreparedStatement pstmt = con.prepareStatement(sql)) {

        pstmt.setString(1, cpf);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            id_show = rs.getString("id");
        }

    } catch (SQLException e) {
        System.out.println("Erro ao acessar banco de dados:");
        e.printStackTrace();
    }

     return id_show;
}

}