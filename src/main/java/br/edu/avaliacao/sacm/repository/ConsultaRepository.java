package br.edu.avaliacao.sacm.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import br.edu.avaliacao.sacm.enums.StatusConsulta;
import br.edu.avaliacao.sacm.model.Consulta;
import br.edu.avaliacao.sacm.model.Medico;
import br.edu.avaliacao.sacm.model.Paciente;

@Repository
public class ConsultaRepository {

    private final JdbcTemplate jdbcTemplate;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaRepository(
            JdbcTemplate jdbcTemplate,
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository) {

        this.jdbcTemplate = jdbcTemplate;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public void salvar(Consulta consulta) {
        String sql = """
            INSERT INTO consultas (
                medico_id,
                paciente_id,
                data_hora,
                motivo,
                status,
                data_agendamento,
                observacao
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.update(
            sql,
            consulta.getMedico().getId(),
            consulta.getPaciente().getId(),
            consulta.getDataHora(),
            consulta.getMotivo(),
            consulta.getStatus().name(),
            consulta.getDataAgendamento(),
            consulta.getObservacao()
        );
    }

    public Optional<Consulta> buscarPorId(Long id) {
        String sql = """
            SELECT *
            FROM consultas
            WHERE id = ?
            """;

        RowMapper<Consulta> mapper = (rs, rowNum) -> {
            Long idBanco = rs.getLong("id");
            Long medicoId = rs.getLong("medico_id");
            Long pacienteId = rs.getLong("paciente_id");

            LocalDateTime dataHora =
                rs.getTimestamp("data_hora").toLocalDateTime();

            String motivo = rs.getString("motivo");
            String status = rs.getString("status");

            LocalDateTime dataAgendamento =
                rs.getTimestamp("data_agendamento").toLocalDateTime();

            String observacao = rs.getString("observacao");

            Medico medico = medicoRepository.buscarPorId(medicoId)
                .orElseThrow(() ->
                    new IllegalStateException("Médico não encontrado: " + medicoId));

            Paciente paciente = pacienteRepository.buscarPorId(pacienteId)
                .orElseThrow(() ->
                    new IllegalStateException("Paciente não encontrado: " + pacienteId));

            return new Consulta(
                idBanco,
                medico,
                paciente,
                dataHora,
                motivo,
                StatusConsulta.valueOf(status),
                dataAgendamento,
                observacao
            );
        };

        return jdbcTemplate.query(sql, mapper, id)
            .stream()
            .findFirst();
    }

    public List<Consulta> listarTodos() {

    String sql = """
        SELECT *
        FROM consultas
        """;

        RowMapper<Consulta> mapper = (rs, rowNum) -> {

    Long idBanco = rs.getLong("id");
    Long medicoId = rs.getLong("medico_id");
    Long pacienteId = rs.getLong("paciente_id");

    LocalDateTime dataHora =
        rs.getTimestamp("data_hora").toLocalDateTime();

    String motivo = rs.getString("motivo");
    String status = rs.getString("status");

    LocalDateTime dataAgendamento =
        rs.getTimestamp("data_agendamento").toLocalDateTime();

    String observacao = rs.getString("observacao");

    Medico medico = medicoRepository.buscarPorId(medicoId)
        .orElseThrow(() ->
            new IllegalStateException("Médico não encontrado: " + medicoId));

    Paciente paciente = pacienteRepository.buscarPorId(pacienteId)
        .orElseThrow(() ->
            new IllegalStateException("Paciente não encontrado: " + pacienteId));

    return new Consulta(
        idBanco,
        medico,
        paciente,
        dataHora,
        motivo,
        StatusConsulta.valueOf(status),
        dataAgendamento,
        observacao
    );
  };
  return jdbcTemplate.query(sql, mapper);
    }

    
    public void atualizar(Consulta consulta) {

    String sql = """
        UPDATE consultas
        SET medico_id = ?,
            paciente_id = ?,
            data_hora = ?,
            motivo = ?,
            status = ?,
            data_agendamento = ?,
            observacao = ?
        WHERE id = ?
        """;

        jdbcTemplate.update(
    sql,
    consulta.getMedico().getId(),
    consulta.getPaciente().getId(),
    consulta.getDataHora(),
    consulta.getMotivo(),
    consulta.getStatus().name(),
    consulta.getDataAgendamento(),
    consulta.getObservacao(),
    consulta.getId()
    );
  }

  public void deletar(Long id) {
    String sql = """
        DELETE FROM consultas
        WHERE id = ?
        """;
        jdbcTemplate.update(sql, id);
}
}