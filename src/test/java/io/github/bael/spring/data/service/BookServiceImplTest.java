package io.github.bael.spring.data.service;

import io.github.bael.spring.data.SpringDataApplication;
import io.github.bael.spring.data.data.AuthorOfBookRepository;
import io.github.bael.spring.data.data.AuthorRepository;
import io.github.bael.spring.data.data.BookRepository;
import io.github.bael.spring.data.data.BooksSpecification;
import io.github.bael.spring.data.entity.Author;
import io.github.bael.spring.data.entity.AuthorOfBook;
import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataApplication.class)
public class BookServiceImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorOfBookRepository authorOfBookRepository;

    @Autowired
    private BookService bookService;
    @Autowired
    private BookTransactionService bookTransactionService;
    private Customer customer1,customer2;
    private Book book1,book2;


    @Before
    public void init() {

        Author mark = new Author();
        mark.setFirstname("Mark");
        mark.setLastname("Twain");

        authorRepository.save(mark);

        Author jules = new Author();
        jules.setFirstname("Jules");
        jules.setLastname("Verne");
        authorRepository.save(jules);

        customer1 = new Customer();
        customer1.setName("Customer1");
        customer1.setAddress("Address1");
        bookTransactionService.save(customer1);

        customer2 = new Customer();
        customer1.setName("Customer2");
        customer1.setAddress("Address2");
        bookTransactionService.save(customer2);

        book1 = new Book();
        book1.setDescription("Увлекательные приключения Тома Сойера");
        book1.setTitle("Приключения Тома Сойера");
        book1.setYear(1876);
        bookRepository.save(book1);

        AuthorOfBook aob1 = new AuthorOfBook();
        aob1.setAuthor(mark);
        aob1.setBook(book1);
        authorOfBookRepository.save(aob1);

        book2 = new Book();
        book2.setTitle("Михаил Строгов");
        book2.setYear(1876);
        bookRepository.save(book2);

        AuthorOfBook aob2 = new AuthorOfBook();
        aob2.setAuthor(mark);
        aob2.setBook(book2);
        authorOfBookRepository.save(aob2);

        AuthorOfBook aob3 = new AuthorOfBook();
        aob3.setAuthor(jules);
        aob3.setBook(book2);
        authorOfBookRepository.save(aob3);

    }

    @Test
    public void testCreation() {
        boolean founded = false;
        for (Book book : bookRepository.findAll()) {
            if (book.getTitle().contains("Тома Сойера")) {
                founded = true;
            }

        }
        assertTrue(founded);

    }

    @Test
    public void testFindByYear() {
        assertEquals(2, bookRepository.findBooksByYear(1876).size());
        assertEquals(0, bookRepository.findBooksByYear(1878).size());
    }

    @Test
    public void testPaging() {

        System.out.println(bookService.findAtPage(1, 1,
                Sort.Direction.ASC, "title")
                .get().findFirst());

        boolean founded = bookService.findAtPage(1, 1,
                Sort.Direction.ASC, "title")
                .get().anyMatch(book ->
                book.getTitle().equals("Приключения Тома Сойера"));

        assertTrue(founded);
    }

    @Test
    public void findSame() {
        Book book = new Book();
        book.setYear(1876);
        assertEquals(2, bookService.findSame(book).size());
    }

    @Test
    public void findInRange() {
        assertEquals(0,
                bookRepository.findAll(
                        BooksSpecification.yearInRange(1874, 1876))
                        .size());

        assertEquals(2, bookRepository.findAll(BooksSpecification.yearInRange(1874, 1877)).size());


    }

    @Test
    public void findByAuthorLastName() {
        assertEquals(2, bookRepository.findByAuthorLastname("Twain").size());
        assertEquals(1, bookRepository.findByAuthorLastname("Verne").size());

    }



    @Test
    public void testComplexQuery() {
        System.out.println(bookRepository.complexQueryMethod());
    }
    @Test
    public void sumOfBookSalesTest(){
        bookTransactionService.doTransaction(customer1,book1,1200.00);
        bookTransactionService.doTransaction(customer2,book1,1200.00);
        Double sumSales = bookTransactionService.sumOfBookSales(book1);
        assertEquals(Double.valueOf(2400), sumSales);
    }
    @Test
    public void sumOfCostPurchasedBooksByCustomerTest(){
        bookTransactionService.doTransaction(customer1,book1,1200.00);
        bookTransactionService.doTransaction(customer1,book2,999.99);
        bookTransactionService.doTransaction(customer1,book1,1200.00);
        Double sum = bookTransactionService.sumOfCostPurchasedBooksByCustomer(customer1);
        assertEquals( Double.valueOf(3399.99), sum);


    }





}