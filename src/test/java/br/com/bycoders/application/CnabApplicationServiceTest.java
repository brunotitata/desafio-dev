package br.com.bycoders.application;

import br.com.bycoders.domain.TransactionDTO;
import br.com.bycoders.domain.exception.CnabInvalidException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CnabApplicationServiceTest {

    private final CnabApplicationService cnabApplicationService = new CnabApplicationService();

    @Test
    public void extractTransactionsToFile() throws IOException {

        byte[] cnab = this.getClass().getResourceAsStream("/file/CNAB.txt").readAllBytes();

        List<TransactionDTO> transactions = cnabApplicationService.extractTransactionsToFile(cnab);

        assertEquals(21, transactions.size());

        assertEquals("09620676017", transactions.get(0).getTaxId());
        assertEquals("55641815063", transactions.get(1).getTaxId());
        assertEquals("84515254073", transactions.get(2).getTaxId());
        assertEquals("09620676017", transactions.get(3).getTaxId());
        assertEquals("09620676017", transactions.get(4).getTaxId());
        assertEquals("84515254073", transactions.get(5).getTaxId());
        assertEquals("84515254073", transactions.get(6).getTaxId());
        assertEquals("23270298056", transactions.get(7).getTaxId());
        assertEquals("55641815063", transactions.get(8).getTaxId());
        assertEquals("84515254073", transactions.get(9).getTaxId());
        assertEquals("23270298056", transactions.get(10).getTaxId());
        assertEquals("23270298056", transactions.get(11).getTaxId());
        assertEquals("55641815063", transactions.get(12).getTaxId());
        assertEquals("84515254073", transactions.get(13).getTaxId());
        assertEquals("23270298056", transactions.get(14).getTaxId());
        assertEquals("55641815063", transactions.get(15).getTaxId());
        assertEquals("84515254073", transactions.get(16).getTaxId());
        assertEquals("23270298056", transactions.get(17).getTaxId());
        assertEquals("84515254073", transactions.get(18).getTaxId());
        assertEquals("23270298056", transactions.get(19).getTaxId());
        assertEquals("84515254073", transactions.get(20).getTaxId());
    }

    @Test
    public void whenUploadingAnInvalidFileAnExceptionMustBeReturned() throws IOException {

        byte[] cnab = this.getClass().getResourceAsStream("/file/img.png").readAllBytes();

        assertThatThrownBy(() -> cnabApplicationService.extractTransactionsToFile(cnab))
                .isInstanceOf(CnabInvalidException.class)
                .hasMessage("Arquivo invalido, não é do tipo CNAB !");
    }

}
