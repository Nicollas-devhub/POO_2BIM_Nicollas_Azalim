package br.edu.avaliacao.sacm.exception;

public class ConflitoHorarioException extends RegraNegocioException {

    private static final long serialVersionUID = 1L;

    // Construtor
    public ConflitoHorarioException(String mensagem) {
        super(mensagem);
    }
}