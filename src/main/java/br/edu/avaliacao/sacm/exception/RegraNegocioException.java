package br.edu.avaliacao.sacm.exception;

public class RegraNegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // Construtor
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}