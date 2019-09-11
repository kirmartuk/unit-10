package io.github.bael.spring.data.service;

import io.github.bael.spring.data.data.BookRepository;
import io.github.bael.spring.data.data.CustomerRepository;
import io.github.bael.spring.data.data.PurchasedBookRepository;
import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;
import io.github.bael.spring.data.entity.PurchasedBook;
import org.springframework.stereotype.Service;

@Service
public class BookTransactionServiceImpl implements BookTransactionService {
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final PurchasedBookRepository purchasedBookRepository;

    public BookTransactionServiceImpl(BookRepository bookRepository, CustomerRepository customerRepository, PurchasedBookRepository purchasedBookRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.purchasedBookRepository = purchasedBookRepository;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }



    @Override
    public void doTransaction(Customer customer, Book book, double cost) {
        PurchasedBook purchasedBook = new PurchasedBook();
        purchasedBook.setCustomer(customer);
        purchasedBook.setBook(book);
        purchasedBook.setCost(cost);
        purchasedBookRepository.save(purchasedBook);
    }

    @Override
    public Double sumOfBookSales(Book book) {
        return purchasedBookRepository
                .findByBook(book)
                .stream()
                .map(purchasedBook -> purchasedBook.getCost())
                .reduce((aDouble, aDouble2) -> Double.sum(aDouble,aDouble2))
                .orElse((double) 0);
    }

    @Override
    public Double sumOfCostPurchasedBooksByCustomer(Customer customer) {
        return purchasedBookRepository
                .findByCustomer(customer)
                .stream()
                .map(purchasedBook -> purchasedBook.getCost())
                .reduce((aDouble, aDouble2) -> Double.sum(aDouble,aDouble2))
                .orElse((double) 0);
    }
}
