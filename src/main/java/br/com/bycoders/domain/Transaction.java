package br.com.bycoders.domain;

import br.com.bycoders.application.utils.CPFUtils;
import br.com.bycoders.domain.exception.CPFInvalidException;
import br.com.bycoders.port.adapters.controller.DTO.MerchantResponseDTO;
import br.com.bycoders.port.adapters.controller.DTO.MerchantResponseDTO.TransactionMerchantDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private UUID transactionId = UUID.randomUUID();

    @Column(name = "transaction_type")
    private Integer transactionType;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "card")
    private String card;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "merchant_name")
    private String merchantName;

    public Transaction(Integer transactionType, LocalDateTime date, BigDecimal amount, String taxId, String card, String ownerName, String merchantName) {
        setTransactionType(transactionType);
        setDate(date);
        setAmount(amount);
        setTaxId(taxId);
        setCard(card);
        setOwnerName(ownerName);
        setMerchantName(merchantName);
    }

    private Transaction() {
    }

    public static Transaction createNewTransaction(TransactionDTO transactionDTO) {
        LocalDate localDate = transactionDTO.getDate().toInstant().atZone(ZoneId.of("UTC-3")).toLocalDate();
        LocalTime localTime = transactionDTO.getHours().toInstant().atZone(ZoneId.of("UTC-3")).toLocalTime();
        return new Transaction(
                transactionDTO.getTransactionType(),
                LocalDateTime.of(localDate, localTime),
                new BigDecimal(transactionDTO.getAmount()),
                transactionDTO.getTaxId(),
                transactionDTO.getCard(),
                transactionDTO.getOwnerName(),
                transactionDTO.getMerchantName()
        );
    }

    public static MerchantResponseDTO createResponse(BigDecimal amount, List<Transaction> transactions) {
        return new MerchantResponseDTO(
                amount,
                transactions.stream().map(
                        transaction -> new TransactionMerchantDTO(
                                transaction.getTransactionType(),
                                transaction.getDate(),
                                transaction.getAmount(),
                                transaction.getTaxId(),
                                transaction.getCard(),
                                transaction.getOwnerName(),
                                transaction.getMerchantName()
                        )
                ).collect(Collectors.toList())
        );
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {

        if (transactionType == null)
            throw new IllegalArgumentException("transactionType não pode ser nulo");

        this.transactionType = transactionType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {

        if (date == null)
            throw new IllegalArgumentException("date não pode ser nulo");

        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {

        if (amount == null) {
            this.amount = BigDecimal.ONE;
            return;
        }

        this.amount = amount.divide(BigDecimal.valueOf(100.00));
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {

        if (taxId == null)
            throw new IllegalArgumentException("taxId não pode ser nulo");

        if (!CPFUtils.isValid(taxId))
            throw new CPFInvalidException("CPF inválido");

        this.taxId = taxId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {

        if (card == null || card.isEmpty())
            throw new IllegalArgumentException("card não pode ser vazio ou nulo");

        this.card = card;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {

        if (ownerName == null || ownerName.isEmpty())
            throw new IllegalArgumentException("ownerName não pode ser vazio ou nulo");

        this.ownerName = ownerName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {

        if (merchantName == null || merchantName.isEmpty())
            throw new IllegalArgumentException("merchantName não pode ser vazio ou nulo");

        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + transactionId +
                ", transactionType=" + transactionType +
                ", date=" + date +
                ", amount=" + amount +
                ", taxId='" + taxId + '\'' +
                ", card='" + card + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionType, that.transactionType) && Objects.equals(date, that.date) && Objects.equals(amount, that.amount) && Objects.equals(taxId, that.taxId) && Objects.equals(card, that.card) && Objects.equals(ownerName, that.ownerName) && Objects.equals(merchantName, that.merchantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionType, date, amount, taxId, card, ownerName, merchantName);
    }
}
