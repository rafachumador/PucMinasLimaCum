import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XDAO {
    private Connection connection;

    public XDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(X x) throws SQLException {
        String sql = "INSERT INTO X (nome, idade) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, x.getNome());
            stmt.setInt(2, x.getIdade());
            stmt.executeUpdate();
        }
    }

    public List<X> listar() throws SQLException {
        List<X> listaX = new ArrayList<>();
        String sql = "SELECT * FROM X";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int idade = resultSet.getInt("idade");
                X x = new X(id, nome, idade);
                listaX.add(x);
            }
        }
        return listaX;
    }

    public void atualizar(X x) throws SQLException {
        String sql = "UPDATE X SET nome=?, idade=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, x.getNome());
            stmt.setInt(2, x.getIdade());
            stmt.setInt(3, x.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM X WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
