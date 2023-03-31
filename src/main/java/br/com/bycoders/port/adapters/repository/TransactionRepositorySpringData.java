package br.com.bycoders.port.adapters.repository;

import br.com.bycoders.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositorySpringData extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByTaxId(String taxId);

    Optional<Transaction> findByDateAndAmountAndTaxId(LocalDateTime date, BigDecimal amount, String taxId);
}
