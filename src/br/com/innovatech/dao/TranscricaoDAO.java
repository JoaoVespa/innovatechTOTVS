package br.com.innovatech.dao;

import br.com.innovatech.model.Transcricao;
import br.com.innovatech.connection.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TranscricaoDAO {

    // CREATE: inserir transcricao
    public boolean inserir(Transcricao t) {
        String sql = "INSERT INTO T_TRANSCRICAO " +
                "(id, id_cliente, texto_bruto, score_churn, valor_budget, status_alerta, produto_sugerido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, t.getId());
            stmt.setInt(2, t.getIdCliente());
            stmt.setString(3, t.getTextoBruto());
            stmt.setDouble(4, t.getScoreRiscoChurn());
            stmt.setDouble(5, t.getValorOrcamento());
            stmt.setString(6, t.getStatusAlerta());
            stmt.setString(7, t.getProdutoSugerido());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir transcricao: " + e.getMessage(), e);
        }
    }

    // READ: listar todas
    public List<Transcricao> listarTodos() {
        List<Transcricao> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_TRANSCRICAO ORDER BY id";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearTranscricao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar transcricoes: " + e.getMessage(), e);
        }

        return lista;
    }

    // READ: buscar por ID
    public Transcricao buscarPorId(int id) {
        String sql = "SELECT * FROM T_TRANSCRICAO WHERE id = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTranscricao(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transcricao: " + e.getMessage(), e);
        }

        return null;
    }

    // UPDATE: atualizar
    public boolean atualizar(Transcricao t) {
        String sql = "UPDATE T_TRANSCRICAO SET " +
                "id_cliente = ?, texto_bruto = ?, score_churn = ?, " +
                "valor_budget = ?, status_alerta = ?, produto_sugerido = ? " +
                "WHERE id = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, t.getIdCliente());
            stmt.setString(2, t.getTextoBruto());
            stmt.setDouble(3, t.getScoreRiscoChurn());
            stmt.setDouble(4, t.getValorOrcamento());
            stmt.setString(5, t.getStatusAlerta());
            stmt.setString(6, t.getProdutoSugerido());
            stmt.setInt(7, t.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar transcricao: " + e.getMessage(), e);
        }
    }

    // DELETE: excluir por ID
    public boolean excluir(int id) {
        String sql = "DELETE FROM T_TRANSCRICAO WHERE id = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir transcricao: " + e.getMessage(), e);
        }
    }

    // Converter uma linha do banco em objeto Java
    private Transcricao mapearTranscricao(ResultSet rs) throws SQLException {
        Transcricao t = new Transcricao();

        t.setId(rs.getInt("id"));
        t.setIdCliente(rs.getInt("id_cliente"));
        t.setTextoBruto(rs.getString("texto_bruto"));
        t.setScoreRiscoChurn(rs.getDouble("score_churn"));
        t.setValorOrcamento(rs.getDouble("valor_budget"));
        t.setStatusAlerta(rs.getString("status_alerta"));
        t.setProdutoSugerido(rs.getString("produto_sugerido"));

        return t;
    }
}
