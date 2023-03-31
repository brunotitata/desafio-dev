package br.com.bycoders.port.adapters.repository;

import br.com.bycoders.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepositorySpringData extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByTaxId(String taxId);

}
