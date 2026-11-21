
package br.edu.avaliacao.sacm.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.edu.avaliacao.sacm.enums.DisponibilidadeMedico;
import br.edu.avaliacao.sacm.enums.Especialidade;

public class Medico extends Pessoa {

    
    private String crm;
    private Especialidade especialidade;
    private DisponibilidadeMedico disponibilidade;
    private BigDecimal valorConsulta;

    
    public Medico(Long id, String nome, String cpf, LocalDate dataNascimento,
                  String email, String telefone, String cidade, String estado,
                  String crm, Especialidade especialidade,
                  DisponibilidadeMedico disponibilidade, BigDecimal valorConsulta) {

        super(id, nome, cpf, dataNascimento, email, telefone, cidade, estado);

        this.crm = crm;
        this.especialidade = especialidade;
        this.disponibilidade = disponibilidade;
        this.valorConsulta = valorConsulta;
    }

    
    @Override
    public String getDescricao() {
        return "Médico: " + getNome() + " | CRM: " + crm;
    }

    public void alterarDisponibilidade(DisponibilidadeMedico disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public boolean isDisponivel() {
        return disponibilidade == DisponibilidadeMedico.DISPONIVEL;
    }

    public boolean podeAtender(Especialidade especialidade) {
        return isDisponivel() && this.especialidade == especialidade;
    }

    
    public String getCrm() {
        return crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public DisponibilidadeMedico getDisponibilidade() {
        return disponibilidade;
    }

    public BigDecimal getValorConsulta() {
        return valorConsulta;
    }
    

    
}
