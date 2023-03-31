package br.com.bycoders.domain;

import br.com.bycoders.domain.exception.CPFInvalidException;
import br.com.bycoders.port.adapters.controller.DTO.MerchantResponseDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    @Test
    public void convertTransactionDtoToTransaction() {

        LocalDateTime date = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        LocalDate localDate = date.toLocalDate();
        LocalTime localTime = date.toLocalTime();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionType(1);
        transactionDTO.setDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        transactionDTO.setAmount("1550.00");
        transactionDTO.setTaxId("72998832016");
        transactionDTO.setCard("8473****1231");
        transactionDTO.setHours(Date.from(localTime.atDate(localDate).atZone(ZoneId.systemDefault()).toInstant()));
        transactionDTO.setOwnerName("JOSÉ COSTA");
        transactionDTO.setMerchantName("MERCEARIA 3 IRMÃOS");

        Transaction transaction = Transaction.createNewTransaction(transactionDTO);

        assertEquals(1, transaction.getTransactionType());
        assertEquals(date, transaction.getDate());
        assertEquals(BigDecimal.valueOf(15.50), transaction.getAmount());
        assertEquals("72998832016", transaction.getTaxId());
        assertEquals("8473****1231", transaction.getCard());
        assertEquals("JOSÉ COSTA", transaction.getOwnerName());
        assertEquals("MERCEARIA 3 IRMÃOS", transaction.getMerchantName());

    }

    @Test
    public void createResponse() {

        List<Transaction> transactions = Arrays.asList(
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
                        2,
                        LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                        BigDecimal.valueOf(2550.00),
                        "72998832016",
                        "8473****1231",
                        "JOSÉ COSTA",
                        "MERCEARIA 3 IRMÃOS"
                ),
                new Transaction(
                        3,
                        LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                        BigDecimal.valueOf(3550.00),
                        "72998832016",
                        "8473****1231",
                        "JOSÉ COSTA",
                        "MERCEARIA 3 IRMÃOS"
                )
        );

        MerchantResponseDTO merchantResponseDTO = Transaction.createResponse(BigDecimal.valueOf(7650.00), transactions);

        assertEquals(BigDecimal.valueOf(7650.00), merchantResponseDTO.getTotalAmount());
        assertEquals(3, merchantResponseDTO.getTransactions().size());

        assertEquals(1, merchantResponseDTO.getTransactions().get(0).getTransactionType());
        assertEquals(LocalDateTime.of(2023, 1, 1, 0, 0, 0), merchantResponseDTO.getTransactions().get(0).getDate());
        assertEquals(BigDecimal.valueOf(15.50), merchantResponseDTO.getTransactions().get(0).getAmount());
        assertEquals("72998832016", merchantResponseDTO.getTransactions().get(0).getTaxId());
        assertEquals("8473****1231", merchantResponseDTO.getTransactions().get(0).getCard());
        assertEquals("JOSÉ COSTA", merchantResponseDTO.getTransactions().get(0).getOwnerName());
        assertEquals("MERCEARIA 3 IRMÃOS", merchantResponseDTO.getTransactions().get(0).getMerchantName());

        assertEquals(2, merchantResponseDTO.getTransactions().get(1).getTransactionType());
        assertEquals(LocalDateTime.of(2023, 1, 1, 0, 0, 0), merchantResponseDTO.getTransactions().get(1).getDate());
        assertEquals(BigDecimal.valueOf(25.50), merchantResponseDTO.getTransactions().get(1).getAmount());
        assertEquals("72998832016", merchantResponseDTO.getTransactions().get(1).getTaxId());
        assertEquals("8473****1231", merchantResponseDTO.getTransactions().get(1).getCard());
        assertEquals("JOSÉ COSTA", merchantResponseDTO.getTransactions().get(1).getOwnerName());
        assertEquals("MERCEARIA 3 IRMÃOS", merchantResponseDTO.getTransactions().get(1).getMerchantName());

        assertEquals(3, merchantResponseDTO.getTransactions().get(2).getTransactionType());
        assertEquals(LocalDateTime.of(2023, 1, 1, 0, 0, 0), merchantResponseDTO.getTransactions().get(2).getDate());
        assertEquals(BigDecimal.valueOf(35.50), merchantResponseDTO.getTransactions().get(2).getAmount());
        assertEquals("72998832016", merchantResponseDTO.getTransactions().get(2).getTaxId());
        assertEquals("8473****1231", merchantResponseDTO.getTransactions().get(2).getCard());
        assertEquals("JOSÉ COSTA", merchantResponseDTO.getTransactions().get(2).getOwnerName());
        assertEquals("MERCEARIA 3 IRMÃOS", merchantResponseDTO.getTransactions().get(2).getMerchantName());

    }

    @Test
    public void createTransaction() {

        Transaction transaction = new Transaction(
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                BigDecimal.valueOf(1550.00),
                "72998832016",
                "8473****1231",
                "JOSÉ COSTA",
                "MERCEARIA 3 IRMÃOS"
        );

        assertEquals(1, transaction.getTransactionType());
        assertEquals(LocalDateTime.of(2023, 1, 1, 0, 0, 0), transaction.getDate());
        assertEquals(BigDecimal.valueOf(15.50), transaction.getAmount());
        assertEquals("72998832016", transaction.getTaxId());
        assertEquals("8473****1231", transaction.getCard());
        assertEquals("JOSÉ COSTA", transaction.getOwnerName());
        assertEquals("MERCEARIA 3 IRMÃOS", transaction.getMerchantName());

    }

    @Test
    public void whenCreateNewTransactionWithCPFInvalidShouldReturnException() {

        try {
            new Transaction(
                    1,
                    LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                    BigDecimal.valueOf(1500.00),
                    "7299883201",
                    "8473****1231",
                    "JOSÉ COSTA",
                    "MERCEARIA 3 IRMÃOS"
            );
        } catch (Exception e) {
            assertEquals("CPF inválido", e.getMessage());
        }

    }

    @Test
    public void whenCreateNewTransactionWithTransactionTypeInvalidShouldReturnException() {

        try {
            new Transaction(
                    0,
                    LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                    BigDecimal.valueOf(1500.00),
                    "72998832016",
                    "8473****1231",
                    "JOSÉ COSTA",
                    "MERCEARIA 3 IRMÃOS"
            );
        } catch (Exception e) {
            assertEquals("Tipo de transação inválido", e.getMessage());
        }
    }

    @Test
    public void whenCreateNewTransactionWithDateInvalidShouldReturnException() {

        assertThatThrownBy(() -> new Transaction(
                1,
                null,
                BigDecimal.valueOf(1500.00),
                "72998832016",
                "8473****1231",
                "JOSÉ COSTA",
                "MERCEARIA 3 IRMÃOS"))
                .hasMessage("date não pode ser nulo")
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void whenCreateNewTransactionWithTaxIdInvalidShouldReturnException() {

        assertThatThrownBy(() -> new Transaction(
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                BigDecimal.valueOf(1500.00),
                null,
                "8473****1231",
                "JOSÉ COSTA",
                "MERCEARIA 3 IRMÃOS"))
                .hasMessage("taxId não pode ser nulo")
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Transaction(
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                BigDecimal.valueOf(1500.00),
                "123456789",
                "8473****1231",
                "JOSÉ COSTA",
                "MERCEARIA 3 IRMÃOS"))
                .hasMessage("CPF inválido")
                .isInstanceOf(CPFInvalidException.class);

    }

    @Test
    public void whenCreateNewTransactionWithCardEmptyShouldReturnException() {

        assertThatThrownBy(() -> new Transaction(
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                BigDecimal.valueOf(1500.00),
                "72998832016",
                "",
                "JOSÉ COSTA",
                "MERCEARIA 3 IRMÃOS"))
                .hasMessage("card não pode ser vazio ou nulo")
                .isInstanceOf(IllegalArgumentException.class);


        assertThatThrownBy(() -> new Transaction(
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                BigDecimal.valueOf(1500.00),
                "72998832016",
                null,
                "JOSÉ COSTA",
                "MERCEARIA 3 IRMÃOS"))
                .hasMessage("card não pode ser vazio ou nulo")
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void whenCreateNewTransactionWithOwnerNameEmptyShouldReturnException() {

        assertThatThrownBy(() -> new Transaction(
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                BigDecimal.valueOf(1500.00),
                "72998832016",
                "8473****1231",
                "",
                "MERCEARIA 3 IRMÃOS"))
                .hasMessage("ownerName não pode ser vazio ou nulo")
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Transaction(
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                BigDecimal.valueOf(1500.00),
                "72998832016",
                "8473****1231",
                null,
                "MERCEARIA 3 IRMÃOS"))
                .hasMessage("ownerName não pode ser vazio ou nulo")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenCreateNewTransactionWithMerchantNameEmptyShouldReturnException() {

        assertThatThrownBy(() -> new Transaction(
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                BigDecimal.valueOf(1500.00),
                "72998832016",
                "8473****1231",
                "JOSÉ COSTA",
                ""))
                .hasMessage("merchantName não pode ser vazio ou nulo")
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Transaction(
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                BigDecimal.valueOf(1500.00),
                "72998832016",
                "8473****1231",
                "JOSÉ COSTA",
                null))
                .hasMessage("merchantName não pode ser vazio ou nulo")
                .isInstanceOf(IllegalArgumentException.class);
    }

    public static Date toDate(Instant instant) {
        BigInteger milis = BigInteger.valueOf(instant.getEpochSecond()).multiply(
                BigInteger.valueOf(1000));
        milis = milis.add(BigInteger.valueOf(instant.getNano()).divide(
                BigInteger.valueOf(1_000_000)));
        return new Date(milis.longValue());
    }
}
