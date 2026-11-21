package br.edu.avaliacao.sacm.model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import br.edu.avaliacao.sacm.enums.StatusConsulta;

public class ConsultaTest {
     @Test
    void deveConfirmarConsultaAgendada() {

        Consulta consulta = new Consulta(
            null, null, null,
            LocalDateTime.now().plusDays(1),
            "Consulta de rotina",
            null
        );

        consulta.confirmar();

        assertEquals(
            StatusConsulta.CONFIRMADA,
            consulta.getStatus()
        );
    }
}

