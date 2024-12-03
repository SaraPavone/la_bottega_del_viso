package sarapavo.la_bottega_del_viso.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sarapavo.la_bottega_del_viso.exceptions.BadRequestException;
import sarapavo.la_bottega_del_viso.exceptions.NotFoundException;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public User findById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public User save(NewUserDTO body) {
        this.usersRepository.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("L'email " + body.email() + " é giá in uso!");
                }
        );

        User newUser = new User(bcrypt.encode(body.password()), body.email(), body.surname(), body.name());
        return this.usersRepository.save(newUser);
    }

    ;


}
