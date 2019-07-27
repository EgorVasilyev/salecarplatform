package ru.job4j.entity.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users_sale")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "role", nullable = false)
    private String role;

    public User(int id, String login, String password, String phone, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && Objects.equals(login, user.login)
                && Objects.equals(phone, user.phone)
                && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, phone, role);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append("}\n");
        return sb.toString();
    }
}
