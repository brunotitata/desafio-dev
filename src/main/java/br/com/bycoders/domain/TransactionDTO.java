package br.com.bycoders.domain;

import java.util.Date;

public class TransactionDTO {

    private Integer transactionType;
    private Date date;
    private String amount;
    private String taxId;
    private String card;
    private Date hours;
    private String ownerName;
    private String merchantName;

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    public Date getHours() {
        return hours;
    }

    public void setHours(Date hours) {
        this.hours = hours;
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
        return "TransactionDTO{" +
                "transactionType=" + transactionType +
                ", date=" + date +
                ", amount='" + amount + '\'' +
                ", taxId='" + taxId + '\'' +
                ", card='" + card + '\'' +
                ", hours=" + hours +
                ", ownerName='" + ownerName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
