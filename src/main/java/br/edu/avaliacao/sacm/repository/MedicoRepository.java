package br.edu.avaliacao.sacm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.edu.avaliacao.sacm.model.Medico;
import br.edu.avaliacao.sacm.enums.Especialidade;
import br.edu.avaliacao.sacm.enums.DisponibilidadeMedico;

@Repository
public class MedicoRepository {

    private final JdbcTemplate jdbcTemplate;

    public MedicoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void salvar(Medico medico) {

    String sql = """
        INSERT INTO medicos (
            nome, cpf, data_nascimento, email, telefone,
            cidade, estado, data_cadastro, status,
            crm, especialidade, disponibilidade, valor_consulta
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    jdbcTemplate.update(
        sql,
        medico.getNome(),
        medico.getCpf(),
        medico.getDataNascimento(),
        medico.getEmail(),
        medico.getTelefone(),
        medico.getCidade(),
        medico.getEstado(),
        medico.getDataCadastro(),
        medico.getStatus().name(),
        medico.getCrm(),
        medico.getEspecialidade().name(),
        medico.getDisponibilidade().name(),
        medico.getValorConsulta()
    );
  }
  
  public Optional<Medico> buscarPorId(Long id) {
    String sql = """
    SELECT *
    FROM medicos
    WHERE id = ?
    """;
    RowMapper<Medico> mapper = (rs, rowNum) -> {
    
        Long idBanco = rs.getLong("id");
    String nome = rs.getString("nome");
    String cpf = rs.getString("cpf");
    LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
    String email = rs.getString("email");
    String telefone = rs.getString("telefone");
    String cidade = rs.getString("cidade");
    String estado = rs.getString("estado");
    String crm = rs.getString("crm");
Especialidade especialidade = Especialidade.valueOf(rs.getString("especialidade"));
DisponibilidadeMedico disponibilidade = DisponibilidadeMedico.valueOf(rs.getString("disponibilidade"));
BigDecimal valorConsulta = rs.getBigDecimal("valor_Consulta");

 return new Medico(
        idBanco,
        nome,
        cpf,
        dataNascimento,
        email,
        telefone,
        cidade,
        estado,
        crm,
        especialidade,
        disponibilidade,
        valorConsulta);
    };    
    
    return jdbcTemplate.query(sql, mapper, id)
        .stream()
        .findFirst();
}

public List<Medico> listarTodos() {
    String sql = """
    SELECT *
    FROM medicos
    """;

    RowMapper<Medico> mapper = (rs, rowNum) -> {

        Long idBanco = rs.getLong("id");
String nome = rs.getString("nome");
String cpf = rs.getString("cpf");
LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
String email = rs.getString("email");
String telefone = rs.getString("telefone");
String cidade = rs.getString("cidade");
String estado = rs.getString("estado");
String crm = rs.getString("crm");
Especialidade especialidade = Especialidade.valueOf(rs.getString("especialidade"));
DisponibilidadeMedico disponibilidade = DisponibilidadeMedico.valueOf(rs.getString("disponibilidade"));
BigDecimal valorConsulta = rs.getBigDecimal("valor_consulta");

return new Medico(
    idBanco,
    nome,
    cpf,
    dataNascimento,
    email,
    telefone,
    cidade,
    estado,
    crm,
    especialidade,
    disponibilidade,
    valorConsulta);
    };
    return jdbcTemplate.query(sql, mapper);
}

 public void atualizar(Medico medico) {
    String sql = """
        UPDATE medicos
        SET nome = ?,
            cpf = ?,
            data_nascimento = ?,
            email = ?,
            telefone = ?,
            cidade = ?,
            estado = ?,
            crm = ?,
            especialidade = ?,
            disponibilidade = ?,
            valor_consulta = ?
        WHERE id = ?
        """;

        jdbcTemplate.update(
        sql,
        medico.getNome(),
        medico.getCpf(),
        medico.getDataNascimento(),
        medico.getEmail(),
        medico.getTelefone(),
        medico.getCidade(),
        medico.getEstado(),
        medico.getCrm(),
        medico.getEspecialidade().name(),
        medico.getDisponibilidade().name(),
        medico.getValorConsulta(),
        medico.getId()
    );
   }
 
   public void deletar(Long id) {
    String sql = """
    DELETE FROM medicos
    WHERE id = ?
    """;
 
    jdbcTemplate.update(sql, id);
}
    
 }
