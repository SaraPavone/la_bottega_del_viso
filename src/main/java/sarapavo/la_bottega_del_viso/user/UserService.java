package sarapavo.la_bottega_del_viso.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sarapavo.la_bottega_del_viso.admin.Admin;
import sarapavo.la_bottega_del_viso.customer.Customer;
import sarapavo.la_bottega_del_viso.exceptions.NotFoundException;

@Service
public class UserService {
    @Autowired
    UsersRepository usersRepository;

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User findById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User save(User user) {
        if (user instanceof Customer) {
        } else if (user instanceof Admin) {
        }
        return usersRepository.save(user);
    }

}