import java.util.Scanner;
import Class.User;
import Class.Login;
import Class.Saque;
import Class.Conta;
import Query.UserDAO;
import Query.LoginDAO;
import Query.ContaDAO;
import Class.Deposito;
import Query.DepositoDAO;
import Query.SaqueDAO;
import Class.Investimentos;
import Query.InvestimentosDAO;
import Api.InflacaoBCB;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean prosseguir = true;
        LoginDAO login_Dao = new LoginDAO();
        ContaDAO contaDAO = new ContaDAO();
        DepositoDAO depositoDAO = new DepositoDAO();
        SaqueDAO saqueDAO = new SaqueDAO();
        InvestimentosDAO investimentosDAO = new InvestimentosDAO();
        boolean Desconectar= false;

        
         while (prosseguir) {
            System.out.println("\n=== MENU ===                  Login:"+login_Dao.MostraToken());
            System.out.println("                              Saldo:"+ contaDAO.MostraSaldo(login_Dao.RetornaId(login_Dao.MostraToken()),Desconectar));
            Desconectar = false;
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("3 - Criar Conta");
            System.out.println("4 - Operações bancárias");
            System.out.println("5 - Sair da Conta ");
            System.out.println("6 - Investimentos");
            System.out.println("7 - Sair ");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                   UserDAO user_Dao = new UserDAO();
                  

                   System.out.println("=== Cadastro de Usuário ===");
                        
                   System.out.print("Nome: ");
                   String nome = scanner.nextLine();
                        
                   System.out.print("CPF: ");
                   String cpf = scanner.nextLine();
                        
                   System.out.print("Email: ");
                   String email = scanner.nextLine();
                        
                   System.out.print("Senha: ");
                   String senha = scanner.nextLine();

                   User user = new User(nome,cpf,email,senha);
                        
                   user_Dao.Insert(user);
                    
                   System.out.println("Fim do cadastro. Obrigado!");
                   break;

                case 2:
                    if (login_Dao.MostraToken().equals("Sem login")) {

                        System.out.print("Nome: ");
                    String nomeLogin = scanner.nextLine();

                    System.out.print("CPF: ");
                    String cpfLogin = scanner.nextLine();

                    System.out.print("Senha: ");
                    String senhaLogin = scanner.nextLine();

                    System.out.println("Verificando login...");

                    Login login = new Login(nomeLogin, cpfLogin, senhaLogin);
                    login_Dao.select(login);
                    }else{
                        System.err.println("É preciso fazer logoff !");
                    }

                    

                    break;
                case 3:

                    if(login_Dao.MostraToken().equals("Sem login")){
                        System.err.println("Você precisa estar logado para criar conta!");
                    }else if(contaDAO.ExisteConta(login_Dao.RetornaId(login_Dao.MostraToken())) == true){ 

                        System.err.println("Você já tem uma conta!");

                    }
                    
                    else{

                    System.out.print("Saldo inicial: ");
                    double saldo = scanner.nextDouble();
                    scanner.nextLine(); // limpar buffer

                    Conta conta = new Conta(login_Dao.MostraToken(),saldo);
                    ContaDAO conta_Dao = new ContaDAO();
                    conta_Dao.criarConta(conta);
                    double conta_id = Double.parseDouble(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken())));
                    Deposito dep = new Deposito(conta_id,saldo);
                    depositoDAO.Insert(dep);
                    }

                    break;
                case 4:
                 if(login_Dao.MostraToken().equals("Sem login")){
                        System.err.println("Você precisa estar logado acessar as operações bancárias!");
                 }else{
                      int opcao1 = 0;
                    boolean operacoesAtivas = true;

                    System.out.println("=== Operações Bancárias ===");

                    while (operacoesAtivas) {
                        System.out.println("\n1. Depósito");
                        System.out.println("2. Saque");
                        System.out.println("3. Extrato");
                        System.out.println("4. Ver inflação");
                        System.out.println("5. Sair");
                        System.out.print("Escolha uma opção: ");
                        opcao1 = scanner.nextInt();

                        switch (opcao1) {
                            case 1:
                                System.out.println("Digite o valor do depósito");
                                double valor = scanner.nextDouble();
                                double conta_id = Double.parseDouble(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken())));

                                Deposito dep = new Deposito(conta_id, valor);
                                depositoDAO.Insert(dep);

                                depositoDAO.AtualizarSaldo(valor,contaDAO.RetornaSaldo(login_Dao.RetornaId(login_Dao.MostraToken())),login_Dao.RetornaId(login_Dao.MostraToken()));
                                break;

                            case 2:

                                System.out.println("Digite o valor do saque");
                                valor = scanner.nextDouble();
                                conta_id = Double.parseDouble(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken())));

                                Saque saq = new Saque(conta_id, valor);

                                if (valor > contaDAO.RetornaSaldo(login_Dao.RetornaId(login_Dao.MostraToken()))) {
                                    System.err.println("Saldo insuficiente!");
                                }else{
                                    saqueDAO.Insert(saq);

                                saqueDAO.AtualizarSaldo(valor,contaDAO.RetornaSaldo(login_Dao.RetornaId(login_Dao.MostraToken())),login_Dao.RetornaId(login_Dao.MostraToken()));
                                }
                                

                                break;
                            
                            case 3:
                                contaDAO.Extrato(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken())));
                                

                                break;
                            case 4:
                                double ipca = InflacaoBCB.obterUltimoIPCA();
                                System.out.printf("Inflação (IPCA) mais recente: %.2f%%\n", ipca);
                                
                                break;

                            case 5:

                               System.out.println("Encerrando operações bancárias...");
                               operacoesAtivas = false;
                                    break;

                                default:
                                    System.out.println("Opção inválida. Tente novamente.");
                            }
                    }
                    
                 }
                 break;
                case 5:
                   
                    System.out.println("Deseja realmente fazer Logoff? s/n ");
                    String logoff = scanner.nextLine();
                     scanner.nextLine(); 
                    System.out.println("Fazendo logoff ...");

                    // Limpa a quebra de linha pendente
                    if(logoff.equals("s")){
                        login_Dao.DesconectarToken();
                        Desconectar = true;
                    }else {
                        System.err.println("Caractere inválido");
                    }


                    break;
                case 6:

                if (login_Dao.MostraToken().equals("Sem login")) {

                    System.err.println("Você precisa estar logado!");
                    
                }else{
                    
                    boolean continuar = true;

                    while (continuar) {
                            System.out.println("\n=== SIMULADOR DE INVESTIMENTOS ===");
                            System.out.println("1. Simular Poupança (0.5% a.m.)");
                            System.out.println("2. Simular CDB (1.1% a.m.)");
                            System.out.println("3. Simular Ações (2% a.m.)");
                            System.out.println("4. Ver investimentos");
                            System.out.println("0. Sair");
                            System.out.print("Escolha uma opção: ");
                            int opcao3 = scanner.nextInt();

                            if (opcao3 == 0) {
                                continuar = false;
                                System.out.println("Encerrando o programa...");
                            }else if(opcao3 == 4){
                               System.out.println(investimentosDAO.verInvestimentos(Double.parseDouble(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken()))))); 
                            } 
                            else if (opcao3 >= 1 && opcao3 <= 3) {
                                System.out.print("Informe o valor do investimento (R$): ");
                                double valor = scanner.nextDouble();

                                System.out.print("Informe o período do investimento (em meses): ");
                                int meses = scanner.nextInt();
                                scanner.nextLine();

                                double resultado = 0.0;

                                switch (opcao3) {
                                    case 1:
                                        int tipo = 1;
                                        resultado = investimentosDAO.calcularPoupanca(valor,meses);
                                        System.out.printf("Valor final na Poupança após %d meses: R$ %.2f%n", meses, resultado);
                                        System.out.println("Deseja investir? s/n");
                                        String investir = scanner.nextLine();
                                        if (investir.equals("s")) {

                                            if (valor > contaDAO.RetornaSaldo(login_Dao.RetornaId(login_Dao.MostraToken()))) {
                                                System.err.println("Saldo insuficiente!");
                                            }else{
                                                Saque saq = new Saque(Double.parseDouble(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken()))), valor);

                                                saqueDAO.Insert(saq);

                                            saqueDAO.AtualizarSaldo(valor,contaDAO.RetornaSaldo(login_Dao.RetornaId(login_Dao.MostraToken())),login_Dao.RetornaId(login_Dao.MostraToken()));
                                            }
                                            Investimentos investimentos = new Investimentos(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken())),tipo,valor,investimentosDAO.calcularPoupanca(valor,meses));
                                            investimentosDAO.Investir(investimentos);
                                        }else if(investir.equals("n")) {

                                            break;
                                        }
                                        break;
                                        
                                    case 2:
                                        tipo = 2;
                                        resultado = investimentosDAO.calcularCDB(valor, meses);
                                        System.out.printf("Valor final no CDB após %d meses: R$ %.2f%n", meses, resultado);
                                        System.out.println("Deseja investir? s/n");
                                        investir = scanner.nextLine();
                                        if (investir.equals("s")) {

                                            if (valor > contaDAO.RetornaSaldo(login_Dao.RetornaId(login_Dao.MostraToken()))) {
                                                System.err.println("Saldo insuficiente!");
                                            }else{
                                                Saque saq = new Saque(Double.parseDouble(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken()))), valor);

                                                saqueDAO.Insert(saq);

                                            saqueDAO.AtualizarSaldo(valor,contaDAO.RetornaSaldo(login_Dao.RetornaId(login_Dao.MostraToken())),login_Dao.RetornaId(login_Dao.MostraToken()));
                                            }
                                            Investimentos investimentos = new Investimentos(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken())),tipo,valor,investimentosDAO.calcularCDB(valor,meses));
                                            investimentosDAO.Investir(investimentos);
                                        }else if(investir.equals("n")) {

                                            break;
                                        }
                                        break;
                                    case 3:
                                        tipo = 3;
                                        resultado = investimentosDAO.calcularAcoes(valor, meses);
                                        System.out.printf("Valor final em Ações após %d meses: R$ %.2f%n", meses, resultado);
                                        System.out.println("Deseja investir? s/n");
                                        investir = scanner.nextLine();
                                        if (investir.equals("s")) {
                                            

                                            if (valor > contaDAO.RetornaSaldo(login_Dao.RetornaId(login_Dao.MostraToken()))) {
                                                System.err.println("Saldo insuficiente!");
                                            }else{
                                                Saque saq = new Saque(Double.parseDouble(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken()))), valor);

                                                saqueDAO.Insert(saq);

                                            saqueDAO.AtualizarSaldo(valor,contaDAO.RetornaSaldo(login_Dao.RetornaId(login_Dao.MostraToken())),login_Dao.RetornaId(login_Dao.MostraToken()));
                                            }
                                            Investimentos investimentos = new Investimentos(contaDAO.RetornaId(login_Dao.RetornaId(login_Dao.MostraToken())),tipo,valor,investimentosDAO.calcularAcoes(valor,meses));
                                            investimentosDAO.Investir(investimentos);
                                        }else if(investir.equals("n")) {

                                            break;
                                        }
                                        break;
                                        
                                }
                            } else {
                                System.out.println("Opção inválida. Tente novamente.");
                            }
                        }
                }
                    break;
                case 7:
                    System.out.println("Saindo do sistema...");
                    prosseguir = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

    
        scanner.close();
    }
}
