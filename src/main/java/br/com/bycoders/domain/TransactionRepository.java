package br.com.bycoders.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    void save(Transaction transaction);

    List<Transaction> findByTaxId(String taxId);

    Optional<Transaction> findByDateAndAmountAndTaxId(LocalDateTime date, BigDecimal amount, String taxId);
}
