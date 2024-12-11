package sarapavo.la_bottega_del_viso.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sarapavo.la_bottega_del_viso.exceptions.BadRequestException;
import sarapavo.la_bottega_del_viso.exceptions.NotFoundException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;


    public User save(NewUserDTO body) {
        this.userRepository.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );
        User newUser = new User(body.name(), body.surname(), body.birthDate(), body.email(), bcrypt.encode(body.password()));

        return this.userRepository.save(newUser);
    }

    public Page<User> findAll(int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.userRepository.findAll(pageable);
    }

    public User findById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdate(Long userId, NewUserDTO body) {

        User found = this.findById(userId);


        if (!found.getEmail().equals(body.email())) {
            this.userRepository.findByEmail(body.email()).ifPresent(

                    user -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }


        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setPassword(body.password());


        return this.userRepository.save(found);
    }

    public void findByIdAndDelete(Long userId) {
        User found = this.findById(userId);
        this.userRepository.delete(found);
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }

    public User findByNameAndSurname(String name, String surname) {
        return this.userRepository.findByNameAndSurname(name, surname).orElseThrow(() -> new NotFoundException("L'utente con nome " + name + " e cognome " + surname + " non é stato trovato"));
    }
}
