package sarapavo.la_bottega_del_viso.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import sarapavo.la_bottega_del_viso.user.User;

import java.util.Date;

@Entity
@Table(name = "customers")

public class Customer extends User {
    private Date birthDate;

    public Customer() {
    }

    public Customer(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Customer(String password, String email, String surname, String name, Date birthDate) {
        super(password, email, surname, name);
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
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                "} " + super.toString();
    }
}
