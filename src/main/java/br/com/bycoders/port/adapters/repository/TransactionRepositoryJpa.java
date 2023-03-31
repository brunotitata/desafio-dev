package br.com.bycoders.port.adapters.repository;

import br.com.bycoders.domain.Transaction;
import br.com.bycoders.domain.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
