import java.sql.*;

public class CadastroSQLite {
    private static final String URL = "jdbc:sqlite:cadastro.db"; // Caminho para o banco de dados SQLite

    public static void main(String[] args) {
        // Criação do banco de dados e da tabela
        criarBancoDeDados();
        
        // Inserção de dados
        inserirCadastro("João Silva", "987654321", "joao.silva@email.com", "Rua Exemplo, 123", "Sem observações");
        inserirCadastro("Maria Oliveira", "998877665", "maria.oliveira@email.com", "Av. Central, 456", "Cliente VIP");
        
        // Exibir cadastros
        exibirCadastros();
    }

    // Cria o banco de dados e a tabela
    public static void criarBancoDeDados() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS cadastro (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                             "nome TEXT NOT NULL, " +
                             "celular TEXT, " +
                             "email TEXT, " +
                             "endereco TEXT, " +
                             "observacao TEXT)";
                stmt.executeUpdate(sql);
                System.out.println("Banco de dados e tabela criados com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao criar banco de dados: " + e.getMessage());
        }
    }

    // Insere um novo cadastro no banco de dados
    public static void inserirCadastro(String nome, String celular, String email, String endereco, String observacao) {
        String sql = "INSERT INTO cadastro(nome, celular, email, endereco, observacao) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nome);
            pstmt.setString(2, celular);
            pstmt.setString(3, email);
            pstmt.setString(4, endereco);
            pstmt.setString(5, observacao);
            
            pstmt.executeUpdate();
            System.out.println("Cadastro inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cadastro: " + e.getMessage());
        }
    }

    // Exibe todos os cadastros no banco de dados
    public static void exibirCadastros() {
        String sql = "SELECT * FROM cadastro";
        
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String celular = rs.getString("celular");
                String email = rs.getString("email");
                String endereco = rs.getString("endereco");
                String observacao = rs.getString("observacao");
                
                System.out.println("ID: " + id + ", Nome: " + nome + ", Celular: " + celular +
                                   ", E-mail: " + email + ", Endereço: " + endereco + ", Observação: " + observacao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao exibir cadastros: " + e.getMessage());
        }
    }
}
