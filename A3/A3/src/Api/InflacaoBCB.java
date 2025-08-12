package Api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class InflacaoBCB {

    public static double obterUltimoIPCA() {
        try {
            // URL da série histórica do IPCA - código 433 do SGS (Banco Central)
            String url = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.433/dados/ultimos/1?formato=json";

            HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
            conexao.setRequestMethod("GET");

            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }
            leitor.close();

            JSONArray json = new JSONArray(resposta.toString());
            JSONObject ultimoDado = json.getJSONObject(0);
            String valorStr = ultimoDado.getString("valor").replace(",", ".");

            return Double.parseDouble(valorStr);

        } catch (Exception e) {
            System.out.println("Erro ao obter inflação: " + e.getMessage());
            return 0.0;
        }
    }

    
}
