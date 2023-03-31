package br.com.bycoders.port.adapters.controller.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MerchantResponseDTO {

    private BigDecimal totalAmount;
    private List<TransactionMerchantDTO> transactions;

    public MerchantResponseDTO(BigDecimal totalAmount, List<TransactionMerchantDTO> transactions) {
        this.totalAmount = totalAmount;
        this.transactions = transactions;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<TransactionMerchantDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionMerchantDTO> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "MerchantResponseDTO{" +
                "totalAmount=" + totalAmount +
                ", transactions=" + transactions +
                '}';
    }

    public static class TransactionMerchantDTO {
        private Integer transactionType;
        private LocalDateTime date;
        private BigDecimal amount;
        private String taxId;
        private String card;
        private String ownerName;
        private String merchantName;

        public TransactionMerchantDTO(Integer transactionType, LocalDateTime date, BigDecimal amount, String taxId, String card, String ownerName, String merchantName) {
            this.transactionType = transactionType;
            this.date = date;
            this.amount = amount;
            this.taxId = taxId;
            this.card = card;
            this.ownerName = ownerName;
            this.merchantName = merchantName;
        }

        public Integer getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(Integer transactionType) {
            this.transactionType = transactionType;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getTaxId() {
            return taxId;
        }

        public void setTaxId(String taxId) {
            this.taxId = taxId;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        @Override
        public String toString() {
            return "TransactionMerchantDTO{" +
                    "transactionType=" + transactionType +
                    ", date=" + date +
                    ", amount=" + amount +
                    ", taxId='" + taxId + '\'' +
                    ", card='" + card + '\'' +
                    ", ownerName='" + ownerName + '\'' +
                    ", merchantName='" + merchantName + '\'' +
                    '}';
        }
    }
}
