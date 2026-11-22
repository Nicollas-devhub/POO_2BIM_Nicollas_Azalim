package br.edu.avaliacao.sacm.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.edu.avaliacao.sacm.model.Paciente;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class PacienteRepository {
    private final JdbcTemplate jdbcTemplate;

    public PacienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Paciente paciente) {
    String sql = """
        INSERT INTO pacientes (
            nome,
            cpf,
            data_nascimento,
            email,
            telefone,
            cidade,
            estado,
            data_cadastro,
            status,
            numero_prontuario,
            convenio,
            contato_emergencia,
            telefone_emergencia
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), 'ATIVO', ?, ?, ?, ?)
        """;

    jdbcTemplate.update(
        sql,
        paciente.getNome(),
        paciente.getCpf(),
        paciente.getDataNascimento(),
        paciente.getEmail(),
        paciente.getTelefone(),
        paciente.getCidade(),
        paciente.getEstado(),
        paciente.getNumeroProntuario(),
        paciente.getConvenio(),
        paciente.getContatoEmergencia(),
        paciente.getTelefoneEmergencia()
    );
}
    

    public Optional<Paciente> buscarPorId(Long id) {
     String sql = """
    SELECT *
    FROM pacientes
    WHERE id = ?
    """;

    RowMapper<Paciente> mapper = (rs, rowNum) -> {
     Long idBanco = rs.getLong("id");
String nome = rs.getString("nome");
   
String cpf = rs.getString("cpf");
LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
String email = rs.getString("email");
String telefone = rs.getString("telefone");
String cidade = rs.getString("cidade");
String estado = rs.getString("estado");

String numeroProntuario = rs.getString("numero_prontuario");
String convenio = rs.getString("convenio");
String contatoEmergencia = rs.getString("contato_emergencia");
String telefoneEmergencia = rs.getString("telefone_emergencia");
      
return new Paciente(
    idBanco,
    nome,
    cpf,
    dataNascimento,
    email,
    telefone,
    cidade,
    estado,
    numeroProntuario,
    convenio,
    contatoEmergencia,
    telefoneEmergencia);
     };

     return jdbcTemplate.query(sql, mapper, id)
        .stream()
        .findFirst();
   }

   public List<Paciente> listarTodos() {
     String sql = """
    SELECT *
    FROM pacientes
    """;

    RowMapper<Paciente> mapper = (rs, rowNum) -> {
      Long idBanco = rs.getLong("id");
String nome = rs.getString("nome");
String cpf = rs.getString("cpf");
LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
String email = rs.getString("email");
String telefone = rs.getString("telefone");
String cidade = rs.getString("cidade");
String estado = rs.getString("estado");

String numeroProntuario = rs.getString("numero_prontuario");
String convenio = rs.getString("convenio");
String contatoEmergencia = rs.getString("contato_emergencia");
String telefoneEmergencia = rs.getString("telefone_emergencia");
     
return new Paciente(
    idBanco,
    nome,
    cpf,
    dataNascimento,
    email,
    telefone,
    cidade,
    estado,
    numeroProntuario,
    convenio,
    contatoEmergencia,
    telefoneEmergencia
      );
  };
  return jdbcTemplate.query(sql, mapper);
 }

 public void atualizar(Paciente paciente) {
     String sql = """
    UPDATE pacientes
    SET nome = ?,
        cpf = ?,
        data_nascimento = ?,
        email = ?,
        telefone = ?,
        cidade = ?,
        estado = ?,
        numero_prontuario = ?,
        convenio = ?,
        contato_emergencia = ?,
        telefone_emergencia = ?
    WHERE id = ?
    """;

    jdbcTemplate.update(
    sql,
    paciente.getNome(),
    paciente.getCpf(),
    paciente.getDataNascimento(),
    paciente.getEmail(),
    paciente.getTelefone(),
    paciente.getCidade(),
    paciente.getEstado(),
    paciente.getNumeroProntuario(),
    paciente.getConvenio(),
    paciente.getContatoEmergencia(),
    paciente.getTelefoneEmergencia(),
    paciente.getId()
      );
  }

  public void deletar(Long id) {
     String sql = """
    DELETE FROM pacientes
    WHERE id = ?
    """;

    jdbcTemplate.update(sql, id);
   }
}
