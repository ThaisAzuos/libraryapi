package io.github.thaisazuoss.libraryapi.repository;

import io.github.thaisazuoss.libraryapi.service.TransacaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
public class TransacoesTest {


    private final TransacaoService transacaoService;

    // Commit -> confirmar as alterações
    //Rollback -> desfazer as alterações
    @Test
    void transacaoSimplesTest(){
        transacaoService.executar();
    }
//    @Test
//    void transacaoEstadoManagedTest(){
//        transacaoService.atualizacaoSemAtualizar();
//    }
}
