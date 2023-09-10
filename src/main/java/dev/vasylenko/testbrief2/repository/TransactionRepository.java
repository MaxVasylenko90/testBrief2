package dev.vasylenko.testbrief2.repository;

import dev.vasylenko.testbrief2.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction>findById(int filter);
    List<Transaction> findByActorContainingIgnoreCase(String filter);
    List<Transaction> findByTypeContainingIgnoreCase(String filter);
    @Query(value = "SELECT * FROM transactions WHERE LOWER(CAST(transaction_data AS CHAR)) LIKE CONCAT('%', :filter, '%')", nativeQuery = true)
    List<Transaction> findByDataContainingIgnoreCase(String filter);


}
