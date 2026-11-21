
package br.edu.avaliacao.sacm.model;

import java.time.LocalDate;

public class Paciente extends Pessoa {

    
    private String numeroProntuario;
    private String convenio;
    private String contatoEmergencia;
    private String telefoneEmergencia;

    
    public Paciente(Long id, String nome, String cpf, LocalDate dataNascimento,
                    String email, String telefone, String cidade, String estado,
                    String numeroProntuario, String convenio,
                    String contatoEmergencia, String telefoneEmergencia) {

        super(id, nome, cpf, dataNascimento, email, telefone, cidade, estado);

        this.numeroProntuario = numeroProntuario;
        this.convenio = convenio;
        this.contatoEmergencia = contatoEmergencia;
        this.telefoneEmergencia = telefoneEmergencia;
    }

    
    public void atualizarConvenio(String convenio) {
        this.convenio = convenio;
    }

    public void atualizarContatoEmergencia(String contato, String telefone) {
        this.contatoEmergencia = contato;
        this.telefoneEmergencia = telefone;
    }

    public boolean podeAgendar() {
        return isAtivo();
    }

    
    public String getNumeroProntuario() {
        return numeroProntuario;
    }

    public String getConvenio() {
        return convenio;
    }

    public String getContatoEmergencia() {
        return contatoEmergencia;
    }

    public String getTelefoneEmergencia() {
        return telefoneEmergencia;
    }

    @Override
public String getDescricao() {
    return "Paciente: " + getNome() + " | Prontuário: " + numeroProntuario;
    }
}
