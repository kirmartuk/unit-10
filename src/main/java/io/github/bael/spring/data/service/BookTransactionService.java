package io.github.bael.spring.data.service;

import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;
import io.github.bael.spring.data.entity.PurchasedBook;

import java.math.BigDecimal;


public interface BookTransactionService {
    void save(Book book);
    void save(Customer customer);
    void doTransaction(Customer customer, Book book, BigDecimal cost);
    BigDecimal sumOfBookSales(Book book);
    BigDecimal sumOfCostPurchasedBooksByCustomer(Customer customer);

}
