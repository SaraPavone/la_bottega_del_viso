package sarapavo.la_bottega_del_viso.user;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@MappedSuperclass
public abstract class User {
    protected String name;
    protected String surname;
    protected String email;
    protected String password;
    @Enumerated(EnumType.STRING)
    protected Role role;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public User() {
    }

    public User(String password, String email, String surname, String name) {
        role = role;
        this.password = password;
        this.email = email;
        this.surname = surname;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
}


