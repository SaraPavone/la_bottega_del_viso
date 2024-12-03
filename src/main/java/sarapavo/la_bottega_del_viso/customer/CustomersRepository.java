package sarapavo.la_bottega_del_viso.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomersRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}

