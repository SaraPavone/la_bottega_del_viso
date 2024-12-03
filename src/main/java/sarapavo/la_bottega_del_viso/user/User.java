package sarapavo.la_bottega_del_viso.user;


public abstract class User {

    protected String name;
    protected String surname;
    protected String email;
    protected String password;
    protected Role role;

    public User() {
    }

    public User(String password, String email, String surname, String name) {
        this.role = role;
        this.password = password;
        this.email = email;
        this.surname = surname;
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


