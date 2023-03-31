package br.com.bycoders.domain;

import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);

    List<Transaction> findByTaxId(String taxId);
}
