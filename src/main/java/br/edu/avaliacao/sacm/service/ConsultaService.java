package br.edu.avaliacao.sacm.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import br.edu.avaliacao.sacm.exception.RegraNegocioException;
import br.edu.avaliacao.sacm.exception.ConflitoHorarioException;
import br.edu.avaliacao.sacm.model.Consulta;
import br.edu.avaliacao.sacm.repository.ConsultaRepository;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void agendar(Consulta consulta) {

        if (consulta.getMedico() == null || consulta.getPaciente() == null) {
            throw new RegraNegocioException(
                "Médico e paciente são obrigatórios."
            );
        }

        if (!consulta.getMedico().isAtivo() || !consulta.getPaciente().isAtivo()) {
            throw new RegraNegocioException(
                "Médico e paciente precisam estar ativos."
            );
        }

        if (!consulta.getMedico().isDisponivel()) {
            throw new RegraNegocioException(
                "O médico não está disponível para atendimento."
            );
        }

        if (!consulta.getDataHora().isAfter(LocalDateTime.now())) {
            throw new RegraNegocioException(
                "A consulta precisa ser agendada para uma data futura."
            );
        }

        if (existeConflitoMedico(consulta)) {
            throw new ConflitoHorarioException(
                "O médico já possui uma consulta nesse horário."
            );
        }

        if (existeConflitoPaciente(consulta)) {
    throw new ConflitoHorarioException(
        "O paciente já possui uma consulta nesse horário."
    );
}

        consultaRepository.salvar(consulta);
    }

    private boolean existeConflitoMedico(Consulta consulta) {

        return consultaRepository.listarTodos().stream()
            .anyMatch(c ->
                c.getMedico().getId().equals(consulta.getMedico().getId()) &&
                c.getDataHora().equals(consulta.getDataHora()) &&
                c.isAtiva()
            );
    }

    private boolean existeConflitoPaciente(Consulta consulta) {
    return consultaRepository.listarTodos().stream()
        .anyMatch(c ->
            c.getPaciente().getId().equals(consulta.getPaciente().getId()) &&
            c.getDataHora().equals(consulta.getDataHora()) &&
            c.isAtiva()
        );
     }

public void confirmar(Long id) {
    Consulta consulta = consultaRepository.buscarPorId(id)
        .orElseThrow(() -> new RegraNegocioException(
            "Consulta não encontrada."
        ));

    consulta.confirmar();

    consultaRepository.atualizar(consulta);
}

public void cancelar(Long id) {
    Consulta consulta = consultaRepository.buscarPorId(id)
        .orElseThrow(() -> new RegraNegocioException(
            "Consulta não encontrada."
        ));

    consulta.cancelar();

    consultaRepository.atualizar(consulta);
        }

        public void finalizar(Long id) {
    Consulta consulta = consultaRepository.buscarPorId(id)
        .orElseThrow(() -> new RegraNegocioException(
            "Consulta não encontrada."
        ));

    consulta.finalizar();

    consultaRepository.atualizar(consulta);
}
}