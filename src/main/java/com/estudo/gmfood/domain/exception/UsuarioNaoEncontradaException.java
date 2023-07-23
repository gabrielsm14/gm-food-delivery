package com.estudo.gmfood.domain.exception;

public class UsuarioNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de usuário com código %d", id));
    }
}
