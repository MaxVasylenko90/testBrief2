package dev.vasylenko.testbrief2.Controller;

import dev.vasylenko.testbrief2.DTO.TransactionDTO;
import dev.vasylenko.testbrief2.DTO.TransactionsResponse;
import dev.vasylenko.testbrief2.repository.TransactionRepository;
import dev.vasylenko.testbrief2.model.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Transactional(readOnly = true)
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public TransactionsResponse findAll() {
        return new TransactionsResponse(transactionRepository.findAll().stream()
                .map(this::convertToTransactionDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public TransactionDTO findOne(@PathVariable("id") Integer id) {
        Transaction transactionFromDb = transactionRepository.findById(id).orElse(null);
        return convertToTransactionDTO(transactionFromDb);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<HttpStatus> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = convertToTransaction(transactionDTO);
        transaction.setDateOfTransaction(new Timestamp(System.currentTimeMillis()));
        transactionRepository.save(transaction);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTransaction(@RequestBody TransactionDTO transactionDTO, @PathVariable("id") Integer id) {
        Transaction transactionUpd = convertToTransaction(transactionDTO);
        Transaction transactionFromDb = transactionRepository.findById(id).orElse(null);
        transactionUpd.setId(id);
        transactionUpd.setDateOfTransaction(transactionFromDb.getDateOfTransaction());
        transactionRepository.save(transactionUpd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable("id") Integer id) {
        transactionRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/search")
    public TransactionsResponse search(@RequestParam("filter") String filter) {
        List<Transaction> combinedList = new ArrayList<>();
        try {
            combinedList.addAll(transactionRepository.findById(Integer.parseInt(filter)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        combinedList.addAll(transactionRepository.findByActorContainingIgnoreCase(filter));
        combinedList.addAll(transactionRepository.findByTypeContainingIgnoreCase(filter));
        combinedList.addAll(transactionRepository.findByDataContainingIgnoreCase(filter));
        return new TransactionsResponse(combinedList.stream()
                .map(this::convertToTransactionDTO).collect(Collectors.toList()));
    }

    @PostMapping("/searchById")
    public TransactionsResponse searchById(@RequestParam("filter") Integer filter) {
        return new TransactionsResponse(transactionRepository.findById(filter).stream()
                .map(this::convertToTransactionDTO).collect(Collectors.toList()));
    }

    @PostMapping("/searchByActor")
    public TransactionsResponse searchByActor(@RequestParam("filter") String filter) {
        return new TransactionsResponse(transactionRepository.findByActorContainingIgnoreCase(filter).stream()
                .map(this::convertToTransactionDTO).collect(Collectors.toList()));
    }

    @PostMapping("/searchByType")
    public TransactionsResponse searchByType(@RequestParam("filter") String filter) {
        return new TransactionsResponse(transactionRepository.findByTypeContainingIgnoreCase(filter).stream()
                .map(this::convertToTransactionDTO).collect(Collectors.toList()));
    }

    @PostMapping("/searchByData")
    public TransactionsResponse searchByData(@RequestParam("filter") String filter) {
        return new TransactionsResponse(transactionRepository.findByDataContainingIgnoreCase(filter).stream()
                .map(this::convertToTransactionDTO).collect(Collectors.toList()));
    }

    private TransactionDTO convertToTransactionDTO(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    private Transaction convertToTransaction(TransactionDTO transactionDTO) {
        return modelMapper.map(transactionDTO, Transaction.class);
    }
}
