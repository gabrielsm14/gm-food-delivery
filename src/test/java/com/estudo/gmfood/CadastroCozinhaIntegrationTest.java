package com.estudo.gmfood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import com.estudo.gmfood.domain.exception.CozinhaNaoEncontradaException;
import com.estudo.gmfood.domain.exception.EntidadeEmUsoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.estudo.gmfood.domain.model.Cozinha;
import com.estudo.gmfood.domain.service.CadastroCozinhaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTest {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Test
    public void deveriaCadastrarCozinhaComSucesso() {
        // cenario
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // ação
        novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

        // validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    public void deveriaFalharAoCadastrarCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cadastroCozinhaService.salvar(novaCozinha);
                });

        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deveriaFalhaAoExcluirCozinhaEmUso() {
        EntidadeEmUsoException exception = Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
           cadastroCozinhaService.excluir(1L);
        });

        assertThat(exception).isNotNull();
    }

    @Test
    public void deveriaFalharAoExcluirCozinhaInexistente() {
        CozinhaNaoEncontradaException exception = Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
            cadastroCozinhaService.excluir(100L);
        });

        assertThat(exception).isNotNull();
    }
}
