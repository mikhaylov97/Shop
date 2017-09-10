package com.tsystems.shop.model;

import com.tsystems.shop.model.enums.UserRoleEnum;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User entity model. This class maps on users Table in our Database.
 * There we store all information about the users. Hibernate forces us to make class
 * with fields and getters and setters for all of them, Serializable interface(optional),
 * and empty constructor if we define custom one.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    /**
     * User ID. It generates by hibernate while inserting.
     * This filed connects with user_id column in users table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    /**
     * Address object. We had Address class {@link Address} and every user may has address
     * information(city, country and so on).
     * By this reason was created addresses table. See Address class declaration for more details {@link Address}.
     * Hibernate allow us to get Address object from User object.
     * This filed connected with address_id column in users table.
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    /**
     * User role. See UserRoleEnum for more details. {@link UserRoleEnum}
     * This filed connected with role column in users table.
     * Cannot be nullable.
     */
    @Column(name = "role", nullable = false)
    private String role;

    /**
     * User name.
     * This filed connected with name column in users table.
     * Cannot be nullable.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * User surname.
     * This filed connected with surname column in users table.
     * Cannot be nullable.
     */
    @Column(name = "surname", nullable = false)
    private String surname;

    /**
     * User birthday.
     * This filed connected with birthday column in users table.
     * Cannot be nullable.
     */
    @Column(name = "birthday")
    private String birthday;

    /**
     * User email.
     * This filed connected with email column in users table.
     * Cannot be nullable and must be unique.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * User password.
     * This filed connected with password column in users table.
     * Cannot be nullable.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * User phone number.
     * This filed connected with phone column in users table.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Empty constructor for Hibernate.
     */
    public User() {
    }

    /**
     * One of our custom constructors to initialize all necessary fields.
     * @param address object. See fields declaration.
     * @param name of the user. See fields declaration.
     * @param surname of the user. See fields declaration.
     * @param birthday of the user. See fields declaration.
     * @param email of user account. See fields declaration.
     * @param password of user account. See fields declaration.
     * @param role of the user. See field declaration and UserRoleEnum {@link UserRoleEnum}
     */
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

    /**
     * One of our custom constructors to initialize all necessary fields.
     * @param address object. See fields declaration.
     * @param name of the user. See fields declaration.
     * @param surname of the user. See fields declaration.
     * @param birthday of the user. See fields declaration.
     * @param email of user account. See fields declaration.
     * @param password of user account. See fields declaration.
     * @param role of the user. See field declaration and UserRoleEnum {@link UserRoleEnum}
     * @param phone number. See field declaration.
     */
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

    /**
     * One of our custom constructors to initialize all necessary fields.
     * @param name of the user. See fields declaration.
     * @param surname of the user. See fields declaration.
     * @param email of user account. See fields declaration.
     * @param password of user account. See fields declaration.
     */
    public User(String email, String name, String surname, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = UserRoleEnum.ROLE_USER.name();
    }

    /**
     * One of our custom constructors to initialize all necessary fields.
     * @param name of the user. See fields declaration.
     * @param surname of the user. See fields declaration.
     * @param email of user account. See fields declaration.
     * @param password of user account. See fields declaration.
     * @param role of the user. See field declaration and UserRoleEnum {@link UserRoleEnum}
     */
    public User(String role, String name, String surname, String email, String password) {
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /**
     * Simple getter
     * @return User ID value
     */
    public long getId() {
        return id;
    }

    /**
     * Simple setter
     * @param id is value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Simple getter
     * @return User address object
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Simple setter
     * @param address is object to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Simple getter
     * @return User name value
     */
    public String getName() {
        return name;
    }

    /**
     * Simple setter
     * @param name is value to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Simple getter
     * @return User surname value
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Simple setter
     * @param surname is value to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Simple getter
     * @return User birthday value
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Simple setter
     * @param birthday is value to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Simple getter
     * @return User email value
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Simple getter
     * @return User password value
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Simple getter
     * @return User role value. See more here {@link UserRoleEnum}
     */
    public String getRole() {
        return role;
    }

    /**
     * Simple setter
     * @param role is value to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Simple getter
     * @return User phone number value
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Simple setter
     * @param phone number is value to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
