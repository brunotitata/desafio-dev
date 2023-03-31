package br.com.bycoders.application;

import br.com.bycoders.domain.Transaction;
import br.com.bycoders.domain.TransactionDTO;
import br.com.bycoders.domain.TransactionRepository;
import br.com.bycoders.port.adapters.controller.DTO.MerchantResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class TransactionApplicationServiceTest {

    private final CnabApplicationService cnabApplicationService = Mockito.mock(CnabApplicationService.class);
    private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);

    private final TransactionApplicationService transactionApplicationService = new TransactionApplicationService(
            cnabApplicationService,
            transactionRepository);

    @Test
    public void shouldSaveTransaction() {

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionType(1);
        transactionDTO.setDate(new Date());
        transactionDTO.setAmount("1550.00");
        transactionDTO.setTaxId("72998832016");
        transactionDTO.setCard("8473****1231");
        transactionDTO.setHours(new Date());
        transactionDTO.setOwnerName("JOSÉ COSTA");
        transactionDTO.setMerchantName("MERCEARIA 3 IRMÃOS");

        given(cnabApplicationService.extractTransactionsToFile(any()))
                .willReturn(List.of(transactionDTO));

        transactionApplicationService.saveTransaction(new byte[0]);

        verify(transactionRepository).save(any());

    }

    @Test
    public void findTransactionByTaxId() {

        given(transactionRepository.findByTaxId(any()))
                .willReturn(List.of(
                        new Transaction(
                                1,
                                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                                BigDecimal.valueOf(1550.00),
                                "72998832016",
                                "8473****1231",
                                "JOSÉ COSTA",
                                "MERCEARIA 3 IRMÃOS"
                        ),
                        new Transaction(
                                1,
                                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                                BigDecimal.valueOf(1550.00),
                                "72998832016",
                                "8473****1231",
                                "JOSÉ COSTA",
                                "MERCEARIA 3 IRMÃOS"
                        )
                ));

        MerchantResponseDTO merchantResponseDTO = transactionApplicationService.operationByTaxId("72998832016");

        verify(transactionRepository).findByTaxId("72998832016");

        assertEquals(BigDecimal.valueOf(31.00), merchantResponseDTO.getTotalAmount());
        assertEquals(2, merchantResponseDTO.getTransactions().size());

        assertEquals("72998832016", merchantResponseDTO.getTransactions().get(0).getTaxId());
        assertEquals("8473****1231", merchantResponseDTO.getTransactions().get(0).getCard());
        assertEquals("JOSÉ COSTA", merchantResponseDTO.getTransactions().get(0).getOwnerName());
        assertEquals("MERCEARIA 3 IRMÃOS", merchantResponseDTO.getTransactions().get(0).getMerchantName());
        assertEquals(1, merchantResponseDTO.getTransactions().get(0).getTransactionType());
        assertEquals("2023-01-01T00:00", merchantResponseDTO.getTransactions().get(0).getDate().toString());
        assertEquals(BigDecimal.valueOf(15.50), merchantResponseDTO.getTransactions().get(0).getAmount());

        assertEquals("72998832016", merchantResponseDTO.getTransactions().get(1).getTaxId());
        assertEquals("8473****1231", merchantResponseDTO.getTransactions().get(1).getCard());
        assertEquals("JOSÉ COSTA", merchantResponseDTO.getTransactions().get(1).getOwnerName());
        assertEquals("MERCEARIA 3 IRMÃOS", merchantResponseDTO.getTransactions().get(1).getMerchantName());
        assertEquals(1, merchantResponseDTO.getTransactions().get(1).getTransactionType());
        assertEquals("2023-01-01T00:00", merchantResponseDTO.getTransactions().get(1).getDate().toString());
        assertEquals(BigDecimal.valueOf(15.50), merchantResponseDTO.getTransactions().get(1).getAmount());

    }

    private static Date toDate(Instant instant) {
        BigInteger milis = BigInteger.valueOf(instant.getEpochSecond()).multiply(
                BigInteger.valueOf(1000));
        milis = milis.add(BigInteger.valueOf(instant.getNano()).divide(
                BigInteger.valueOf(1_000_000)));
        return new Date(milis.longValue());
    }

}
