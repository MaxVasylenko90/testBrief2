package dev.vasylenko.testbrief2.model;

import dev.vasylenko.testbrief2.util.TransactionDataConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Map;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date_of_transaction")
    private Timestamp dateOfTransaction;
    @Column(name = "typee")
    @NotEmpty(message = "Type can't be empty!")
    private String type;
    @Column(name = "actor")
    @NotEmpty(message = "Actor can't be empty!")
    private String actor;

//    @Convert(converter = TransactionDataConverter.class)
    @Column(name = "transaction_data")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> transactionData;

    public Transaction(String type, String actor, Map<String, String> transactionData) {
        this.type = type;
        this.actor = actor;
        this.transactionData = transactionData;
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Timestamp dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Map<String, String> getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(Map<String, String> transactionData) {
        this.transactionData = transactionData;
    }
}
