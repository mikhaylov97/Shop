package com.tsystems.shop.model;

import com.tsystems.shop.model.enums.UserRoleEnum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    public User() {
    }

    public User(Address address, String name,
                String surname, String birthday, String email,
                String password, String role) {
        this.address = address;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(Address address, String name,
                String surname, String birthday, String email,
                String password, String role, String phone) {
        this.address = address;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
    }

    public User(String email, String name, String surname, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = UserRoleEnum.ROLE_USER.name();
    }

    public User(String role, String name, String surname, String email, String password) {
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
