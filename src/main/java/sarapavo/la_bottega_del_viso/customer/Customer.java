package sarapavo.la_bottega_del_viso.customer;

import jakarta.persistence.*;
import sarapavo.la_bottega_del_viso.user.Role;
import sarapavo.la_bottega_del_viso.user.User;

import java.util.Date;

@Entity
@Table(name = "customers")

public class Customer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private Date birthDate;

    public Customer() {
    }

    public Customer(Long id, Date birthDate) {
        this.id = id;
        this.birthDate = birthDate;
    }

    public Customer(Long id, Role role, String password, String email, String surname, String name, Long id1, Date birthDate) {
        super(role, password, email, surname, name);
        this.id = id;
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "birthDate=" + birthDate +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                "} " + super.toString();
    }
}
