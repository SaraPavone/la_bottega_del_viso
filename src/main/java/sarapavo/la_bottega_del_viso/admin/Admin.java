package sarapavo.la_bottega_del_viso.admin;


import jakarta.persistence.*;
import sarapavo.la_bottega_del_viso.user.Role;
import sarapavo.la_bottega_del_viso.user.User;


@Entity
@Table(name = "admins")


public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Admin() {
    }

    public Admin(Long id, Role role, String password, String email, String surname, String name) {
        super(role, password, email, surname, name);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                "} " + super.toString();
    }
}
