import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/seu_banco", "seu_usuario", "sua_senha");
            XDAO xdao = new XDAO(connection);

            Scanner scanner = new Scanner(System.in);
            int opcao = 0;

            while (opcao != 5) {
                System.out.println("Escolha uma opção:");
                System.out.println("1. Listar");
                System.out.println("2. Inserir");
                System.out.println("3. Atualizar");
                System.out.println("4. Excluir");
                System.out.println("5. Sair");
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        listarRegistros(xdao);
                        break;
                    case 2:
                        inserirRegistro(xdao, scanner);
                        break;
                    case 3:
                        atualizarRegistro(xdao, scanner);
                        break;
                    case 4:
                        excluirRegistro(xdao, scanner);
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarRegistros(XDAO xdao) throws SQLException {
        List<X> listaX = xdao.listar();
        if (listaX.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
        } else {
            for (X x : listaX) {
                System.out.println(x);
            }
        }
    }

    private static void inserirRegistro(XDAO xdao, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Digite o nome:");
        String nome = scanner.nextLine();
        System.out.println("Digite a idade:");
        int idade = scanner.nextInt();

        X novoX = new X(0, nome, idade); // O id será gerado pelo banco
        xdao.inserir(novoX);
        System.out.println("Registro inserido com sucesso.");
    }

    private static void atualizarRegistro(XDAO xdao, Scanner scanner) throws SQLException {
        System.out.println("Digite o ID do registro a ser atualizado:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o novo nome:");
        String nome = scanner.nextLine();
        System.out.println("Digite a nova idade:");
        int idade = scanner.nextInt();

        X xAtualizado = new X(id, nome, idade);
        xdao.atualizar(xAtualizado);
        System.out.println("Registro atualizado com sucesso.");
    }

    private static void excluirRegistro(XDAO xdao, Scanner scanner) throws SQLException {
        System.out.println("Digite o ID do registro a ser excluído:");
        int id = scanner.nextInt();

        xdao.excluir(id);
        System.out.println("Registro excluído com sucesso.");
    }
}
