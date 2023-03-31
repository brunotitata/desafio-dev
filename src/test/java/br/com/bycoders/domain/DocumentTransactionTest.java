package br.com.bycoders.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentTransactionTest {

    @Test
    public void getEnumByTransactionType() {

        assertEquals(DocumentTransaction.DEBITO, DocumentTransaction.of(1));
        assertEquals(DocumentTransaction.BOLETO, DocumentTransaction.of(2));
        assertEquals(DocumentTransaction.FINANCIAMENTO, DocumentTransaction.of(3));
        assertEquals(DocumentTransaction.CREDITO, DocumentTransaction.of(4));
        assertEquals(DocumentTransaction.RECEBIMENTO_EMPRESTIMO, DocumentTransaction.of(5));
        assertEquals(DocumentTransaction.VENDAS, DocumentTransaction.of(6));
        assertEquals(DocumentTransaction.RECEBIMENTO_TED, DocumentTransaction.of(7));
        assertEquals(DocumentTransaction.RECEBIMENTO_DOC, DocumentTransaction.of(8));
        assertEquals(DocumentTransaction.ALUGUEL, DocumentTransaction.of(9));

        assertThatThrownBy(() -> DocumentTransaction.of(10))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("TransactionType não encontrado com valor: 10");

    }
}
