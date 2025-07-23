package io.github.ruan_pablo_oli.library.repository;

import io.github.ruan_pablo_oli.library.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;


@SpringBootTest
public class TransactionsTest {

    @Autowired
    private AutorRepository autorRepository;


    @Autowired
    private TransacaoService transacaoService;




    /**
     * Commit -> Confirmar alterações
     * Rooback -> desfazer as alterações
     */
    @Test
    void transacaoSimples(){
        // salvaar um livro
        // salvar o autor
        // alugar o livro
        // enviar email pro locatario
        // notificar que o livro saiu da livraria
        transacaoService.executar();

    }

    @Test
    void transacaoEstadoManaged(){
        transacaoService.atualicavaoSemAtualizar();
    }

}
