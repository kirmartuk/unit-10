package io.github.bael.spring.data.data;

import io.github.bael.spring.data.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
