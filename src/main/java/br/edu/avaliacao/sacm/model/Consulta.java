
package br.edu.avaliacao.sacm.model;

import java.time.LocalDateTime;

import br.edu.avaliacao.sacm.enums.StatusConsulta;
import br.edu.avaliacao.sacm.exception.RegraNegocioException;

public class Consulta {

    
    private Long id;
    private Medico medico;
    private Paciente paciente;
    private LocalDateTime dataHora;
    private String motivo;
    private StatusConsulta status;
    private LocalDateTime dataAgendamento;
    private String observacao;

    
    public Consulta(Long id, Medico medico, Paciente paciente,
                    LocalDateTime dataHora, String motivo,
                    String observacao) {

        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.dataHora = dataHora;
        this.motivo = motivo;
        this.status = StatusConsulta.AGENDADA;
        this.dataAgendamento = LocalDateTime.now();
        this.observacao = observacao;
    }

    
   public void confirmar() {
    if (status != StatusConsulta.AGENDADA) {
        throw new RegraNegocioException(
            "Não é possível confirmar uma consulta que não está agendada."
        );
    }

    this.status = StatusConsulta.CONFIRMADA;
}

  public void cancelar() {
    if (status != StatusConsulta.AGENDADA &&
        status != StatusConsulta.CONFIRMADA) {

        throw new RegraNegocioException(
            "Não é possível cancelar uma consulta que já foi finalizada ou cancelada."
        );
    }

    this.status = StatusConsulta.CANCELADA;
}

   public void finalizar() {
    if (status != StatusConsulta.CONFIRMADA) {
        throw new RegraNegocioException(
            "Não é possível finalizar uma consulta que não está confirmada."
        );
    }

    this.status = StatusConsulta.FINALIZADA;
}

    public boolean isAtiva() {
        return status == StatusConsulta.AGENDADA ||
               status == StatusConsulta.CONFIRMADA;
    }

    public String getResumo() {
        return "Consulta: " + id +
               " | Médico: " + medico.getNome() +
               " | Paciente: " + paciente.getNome() +
               " | Data: " + dataHora +
               " | Status: " + status;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public Consulta(Long id, Medico medico, Paciente paciente,
                LocalDateTime dataHora, String motivo,
                StatusConsulta status, LocalDateTime dataAgendamento,
                String observacao) {
      
    this.id = id;
    this.medico = medico;
    this.paciente = paciente;
    this.dataHora = dataHora;
    this.motivo = motivo;
    this.status = status;
    this.dataAgendamento = dataAgendamento;
    this.observacao = observacao;
}

}
