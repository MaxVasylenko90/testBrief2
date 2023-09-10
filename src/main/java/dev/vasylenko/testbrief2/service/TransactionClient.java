package dev.vasylenko.testbrief2.service;

import dev.vasylenko.testbrief2.DTO.TransactionsResponse;
import dev.vasylenko.testbrief2.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.*;

import java.util.List;

@HttpExchange("/transactions")
public interface TransactionClient {
    @GetExchange
    List<Transaction> findAll();

    @GetExchange("/{id}")
    Transaction findOne(@PathVariable Integer id);

    @PostExchange
    ResponseEntity<HttpStatus> saveTransaction(@RequestBody Transaction transaction);

    @PatchExchange("/{id}")
    ResponseEntity<HttpStatus> updateTransaction(@RequestBody Transaction transaction, @PathVariable Integer id);

    @DeleteExchange("/{id}")
    ResponseEntity<HttpStatus> deleteTransaction(@PathVariable Integer id);

    @PostExchange("/search")
    TransactionsResponse search(@RequestParam("filter") String filter);

    @PostMapping("/searchById")
    TransactionsResponse searchById(@RequestParam("filter") Integer filter);

    @PostMapping("/searchByActor")
    TransactionsResponse searchByActor(@RequestParam("filter") String filter);

    @PostMapping("/searchByType")
    TransactionsResponse searchByType(@RequestParam("filter") String filter);

    @PostMapping("/searchByData")
    TransactionsResponse searchByData(@RequestParam("filter") String filter);
}
