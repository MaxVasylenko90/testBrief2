package dev.vasylenko.testbrief2.DTO;

import java.util.List;

public class TransactionsResponse {
    private List<TransactionDTO> transactionDTOList;

    public TransactionsResponse(List<TransactionDTO> transactionDTOList) {
        this.transactionDTOList = transactionDTOList;
    }

    public List<TransactionDTO> getTransactionDTOList() {
        return transactionDTOList;
    }

    public void setTransactionDTOList(List<TransactionDTO> transactionDTOList) {
        this.transactionDTOList = transactionDTOList;
    }
}
