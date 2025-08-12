package Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import BD.Conexao;
import Class.Investimentos;

public class InvestimentosDAO {
    
    public void Investir(Investimentos investimentos ){
        String sql = "INSERT INTO investimentos (conta_id, tipo_id, valor, rendimento_esperado) VALUES (?, ?,?,?)";

       try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, investimentos.getContaId());
            stmt.setInt(2, investimentos.getTipoId());
            stmt.setDouble(3, investimentos.getValor());
            stmt.setDouble(4, investimentos.getRendimentoEsperado());
            
            stmt.executeUpdate();
            
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar conta bancária:");
            e.printStackTrace();
        }
        
    }

    public String verInvestimentos( double conta_id) {
    String sql = "SELECT investimentos.id, tipos_investimento.nome AS tipo, valor, data_investimento, rendimento_esperado " +
                 "FROM investimentos " +
                 "INNER JOIN tipos_investimento ON investimentos.tipo_id = tipos_investimento.id " +
                 "WHERE conta_id = ?";

    StringBuilder resultado = new StringBuilder();

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDouble(1, conta_id);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String id = Integer.toString(rs.getInt("id"));
            String tipo = rs.getString("tipo");
            String valor = Double.toString(rs.getDouble("valor"));
            String dataInvestimento = rs.getString("data_investimento");
            String rendimentoEsperado = Double.toString(rs.getDouble("rendimento_esperado"));

            resultado.append("ID: ").append(id)
                     .append(" | Tipo: ").append(tipo)
                     .append(" | Valor: R$ ").append(valor)
                     .append(" | Data: ").append(dataInvestimento)
                     .append(" | Rendimento Esperado: ").append(rendimentoEsperado)
                     .append("\n");
        }

    } catch (SQLException e) {
        System.out.println("Erro ao consultar investimentos:");
        e.printStackTrace();
        return "Erro ao consultar investimentos.";
    }

    return resultado.length() > 0 ? resultado.toString() : "Nenhum investimento encontrado.";
}

    
    public  double calcularPoupanca(double valorInicial, int meses) {
        double taxa = 0.005; // 0.5%
        double rendimento_esperado = (valorInicial * Math.pow(1 + taxa, meses)) - valorInicial;
        return rendimento_esperado;
    }

    // CDB: 1.1% ao mês
    public  double calcularCDB(double valorInicial, int meses) {
        double taxa = 0.011; // 1.1%
        double rendimento_esperado = (valorInicial * Math.pow(1 + taxa, meses)) - valorInicial;
         return rendimento_esperado;
    }

    // Ações: 2% ao mês (média simulada)
    public  double calcularAcoes(double valorInicial, int meses) {
        double taxa = 0.02; // 2%
        double rendimento_esperado = (valorInicial * Math.pow(1 + taxa, meses)) - valorInicial;
         return rendimento_esperado;
    }
}
