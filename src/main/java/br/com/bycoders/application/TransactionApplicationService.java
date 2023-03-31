package br.com.bycoders.application;

import br.com.bycoders.domain.DocumentTransaction;
import br.com.bycoders.domain.Transaction;
import br.com.bycoders.domain.TransactionRepository;
import br.com.bycoders.port.adapters.controller.DTO.MerchantResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Component
public class TransactionApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionApplicationService.class);


    private final CnabApplicationService cnabApplicationService;
    private final TransactionRepository transactionRepository;

    public TransactionApplicationService(CnabApplicationService cnabApplicationService, TransactionRepository transactionRepository) {
        this.cnabApplicationService = cnabApplicationService;
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(byte[] cnabFile) {

        cnabApplicationService.extractTransactionsToFile(cnabFile)
                .stream()
                .map(Transaction::createNewTransaction)
                .forEach(transaction -> transactionRepository.findByDateAndAmountAndTaxId(transaction.getDate(), transaction.getAmount(), transaction.getTaxId())
                        .ifPresentOrElse(
                                (value) -> LOG.info("Transação já existente" + value),
                                () -> transactionRepository.save(transaction)
                        ));

    }

    public MerchantResponseDTO operationByTaxId(String taxId) {

        List<Transaction> transactions = transactionRepository.findByTaxId(taxId);

        Set<DocumentTransaction> naturezaSum = EnumSet.of(
                DocumentTransaction.DEBITO,
                DocumentTransaction.CREDITO,
                DocumentTransaction.RECEBIMENTO_EMPRESTIMO,
                DocumentTransaction.VENDAS,
                DocumentTransaction.RECEBIMENTO_TED,
                DocumentTransaction.RECEBIMENTO_DOC
        );

        Set<DocumentTransaction> naturezaDebit = EnumSet.of(
                DocumentTransaction.BOLETO,
                DocumentTransaction.FINANCIAMENTO,
                DocumentTransaction.ALUGUEL
        );

        BigDecimal sum = transactions.stream()
                .filter(transaction -> naturezaSum.contains(DocumentTransaction.of(transaction.getTransactionType())))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal debit = transactions.stream()
                .filter(transaction -> naturezaDebit.contains(DocumentTransaction.of(transaction.getTransactionType())))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalValue = sum.subtract(debit);

        return Transaction.createResponse(totalValue, transactions);

    }


}
