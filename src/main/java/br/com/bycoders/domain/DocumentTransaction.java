package br.com.bycoders.domain;

import java.util.Arrays;
import java.util.Objects;

public enum DocumentTransaction {
    DEBITO(1, "Débito", "+"),
    BOLETO(2, "Boleto", "-"),
    FINANCIAMENTO(3, "Financiamento", "-"),
    CREDITO(4, "Crédito", "+"),
    RECEBIMENTO_EMPRESTIMO(5, "Recebimento Empréstimo", "+"),
    VENDAS(6, "Vendas", "+"),
    RECEBIMENTO_TED(7, "Recebimento TED", "+"),
    RECEBIMENTO_DOC(8, "Recebimento DOC", "+"),
    ALUGUEL(9, "Aluguel", "-");

    private Integer type;
    private String nature;
    private String signal;

    DocumentTransaction(Integer type, String nature, String signal) {
        this.type = type;
        this.nature = nature;
        this.signal = signal;
    }

    public static DocumentTransaction of(Integer transactionType) {
        return Arrays.stream(DocumentTransaction.values())
                .filter(p -> Objects.nonNull(transactionType) && p.type.equals(transactionType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("TransactionType não encontrado com valor: " + transactionType));
    }
}
