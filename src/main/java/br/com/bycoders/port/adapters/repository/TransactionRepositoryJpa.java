package br.com.bycoders.port.adapters.repository;

import br.com.bycoders.domain.Transaction;
import br.com.bycoders.domain.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryJpa implements TransactionRepository {

    private TransactionRepositorySpringData transactionRepositorySpringData;

    public TransactionRepositoryJpa(TransactionRepositorySpringData transactionRepositorySpringData) {
        this.transactionRepositorySpringData = transactionRepositorySpringData;
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepositorySpringData.save(transaction);

    }

    @Override
    public List<Transaction> findByTaxId(String taxId) {
        return transactionRepositorySpringData.findByTaxId(taxId);
    }

    @Override
    public Optional<Transaction> findByDateAndAmountAndTaxId(LocalDateTime date, BigDecimal amount, String taxId) {
        return transactionRepositorySpringData.findByDateAndAmountAndTaxId(date, amount, taxId);
    }
}
