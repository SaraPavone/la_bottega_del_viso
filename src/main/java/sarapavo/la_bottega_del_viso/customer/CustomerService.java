package sarapavo.la_bottega_del_viso.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sarapavo.la_bottega_del_viso.exceptions.BadRequestException;
import sarapavo.la_bottega_del_viso.exceptions.NotFoundException;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomersRepository customersRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public Customer findById(Long id) {
        return customersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Customer findByEmail(String email) {
        return customersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }

    public List<Customer> findAll() {
        return customersRepository.findAll();
    }

    public Customer save(NewCustomerDTO body) {
        this.customersRepository.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("L'email " + body.email() + " é giá in uso!");
                }
        );

        Customer customer = new Customer(body.password(), body.email(), body.surname(), body.name(), body.birthDate());
        return this.customersRepository.save(customer);
    }

    ;


}
