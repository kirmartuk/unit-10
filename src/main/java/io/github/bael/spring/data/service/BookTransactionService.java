package io.github.bael.spring.data.service;

import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;
import io.github.bael.spring.data.entity.PurchasedBook;


public interface BookTransactionService {
    void save(Book book);
    void save(Customer customer);
    void doTransaction(Customer customer, Book book, double cost);
    Double sumOfBookSales(Book book);
    Double sumOfCostPurchasedBooksByCustomer(Customer customer);

}
