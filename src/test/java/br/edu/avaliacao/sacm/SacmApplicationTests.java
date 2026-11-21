package br.edu.avaliacao.sacm;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.avaliacao.sacm.enums.StatusConsulta;
import br.edu.avaliacao.sacm.model.Consulta;

@SpringBootTest
class SacmApplicationTests {

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
