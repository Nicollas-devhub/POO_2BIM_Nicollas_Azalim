package br.edu.avaliacao.sacm.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import br.edu.avaliacao.sacm.enums.StatusPessoa;

public abstract class Pessoa {

    
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String cidade;
    private String estado;
    private LocalDateTime dataCadastro;
    private StatusPessoa status;

    
    public Pessoa(Long id, String nome, String cpf, LocalDate dataNascimento,
                  String email, String telefone, String cidade, String estado) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
        this.dataCadastro = LocalDateTime.now();
        this.status = StatusPessoa.ATIVO;
    }

    // Métodos
    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public void atualizarContato(String email, String telefone, String cidade, String estado) {
        this.email = email;
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
    }

    public void ativar() {
        this.status = StatusPessoa.ATIVO;
    }

    public void desativar() {
        this.status = StatusPessoa.INATIVO;
    }

    public boolean isAtivo() {
        return this.status == StatusPessoa.ATIVO;
    }

    public abstract String getDescricao();

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public StatusPessoa getStatus() {
        return status;
    }
}