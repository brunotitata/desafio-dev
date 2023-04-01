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
import java.util.stream.Collectors;

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

        BigDecimal positiveValues = calculateSumAmount(transactions);
        BigDecimal negativeValues = calculateDebitAmount(transactions);

        BigDecimal totalValue = positiveValues.subtract(negativeValues);

        return Transaction.createResponse(totalValue, transactions);

    }

    private BigDecimal calculateDebitAmount(List<Transaction> transactions) {

        Set<DocumentTransaction> naturezaDebit = EnumSet.of(
                DocumentTransaction.BOLETO,
                DocumentTransaction.FINANCIAMENTO,
                DocumentTransaction.ALUGUEL
        );
        return transactions.stream()
                .filter(transaction -> naturezaDebit.contains(DocumentTransaction.of(transaction.getTransactionType())))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateSumAmount(List<Transaction> transactions) {

        Set<DocumentTransaction> naturezaSum = EnumSet.of(
                DocumentTransaction.DEBITO,
                DocumentTransaction.CREDITO,
                DocumentTransaction.RECEBIMENTO_EMPRESTIMO,
                DocumentTransaction.VENDAS,
                DocumentTransaction.RECEBIMENTO_TED,
                DocumentTransaction.RECEBIMENTO_DOC
        );

        return transactions.stream()
                .filter(transaction -> naturezaSum.contains(DocumentTransaction.of(transaction.getTransactionType())))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MerchantResponseDTO> allMerchants() {
        return transactionRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getTaxId))
                .entrySet()
                .stream()
                .map(key -> {
                    BigDecimal positiveValues = calculateSumAmount(key.getValue());
                    BigDecimal negativeValues = calculateDebitAmount(key.getValue());
                    BigDecimal totalValue = positiveValues.subtract(negativeValues);
                    return Transaction.createResponse(totalValue, key.getValue());
                }).collect(Collectors.toList());
    }

}
