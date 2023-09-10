package dev.vasylenko.testbrief2.DTO;

import dev.vasylenko.testbrief2.util.TransactionDataConverter;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Map;

public class TransactionDTO {
    @NotEmpty(message = "Type can't be empty!")
    private String type;
    @NotEmpty(message = "Actor can't be empty!")
    private String actor;
    private Timestamp dateOfTransaction;
//    @Convert(converter = TransactionDataConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> transactionData;

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

    public Timestamp getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Timestamp dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }
}
