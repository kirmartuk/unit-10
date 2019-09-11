package io.github.bael.spring.data.data;

import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;
import io.github.bael.spring.data.entity.PurchasedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PurchasedBookRepository extends CrudRepository<PurchasedBook, Long>
, JpaRepository<PurchasedBook, Long> {
    List<PurchasedBook> findByBook(Book book);
    List<PurchasedBook> findByCustomer(Customer customer);
}
