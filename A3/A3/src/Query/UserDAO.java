
package Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Class.User;
import BD.Conexao;

public class UserDAO{
    public void Insert(User user){
        String sql = "INSERT INTO clientes (nome, cpf, email, senha) VALUES (?, ?, ?, ?)";

        try{

        Connection con = Conexao.conectar();
        PreparedStatement pstmt = con.prepareStatement(sql);


            pstmt.setString(1, user.getNome());
            pstmt.setString(2, user.getCpf());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getSenha());

             int rowsAffected = pstmt.executeUpdate();

             if (rowsAffected > 0) {
                System.out.println("Usuário inserido com sucesso!");
            } else {
                System.out.println("Erro ao inserir usuário.");
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao acessar banco de dados:");
            e.printStackTrace();
        
    }
    }   

    
}
