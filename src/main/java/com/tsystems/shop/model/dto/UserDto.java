package com.tsystems.shop.model.dto;


/**
 * Class which represents necessary form of the User model.
 * This dto is used almost in all controllers, cause it contains all
 * necessary information about user and money that he spent in our store.
 * See {@link com.tsystems.shop.model.User}
 */
public class UserDto {
    /**
     * User ID
     */
    private long id;
    /**
     * User's name
     */
    private String name;
    /**
     * User's surname
     */
    private String surname;
    /**
     * User's phone number in string format
     */
    private String phone;
    /**
     * User's email. It's identifier of user as well as the his ID
     */
    private String email;
    /**
     * User's address in pleasant form -(Country, City (postcode), Street House, Apartment
     */
    private String address;
    /**
     * User's birthday in the next form - (dd-MM-yyyy)
     */
    private String birthday;
    /**
     * User's money he spent in out store
     */
    private String totalCash;

    /**
     * Simple getter of the ID
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Simple setter of the ID
     * @param id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Simple getter of the User's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Simple setter of the User's name
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Simple getter of the User's surname
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Simple setter of the User's surname
     * @param surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Simple getter of the User's phone number
     * @return phone number in string format. See above.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Simple setter of the User's phone number
     * @param phone to set. Must be in correct string format. See above.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Simple getter of the User's address.
     * @return address in string format. See above.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Simple setter of the User's address.
     * @param address to set. It may be any string. But will be better to set
     *                string in format above. See field declaration.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Simple getter of the User's birthday.
     * @return birthday field.
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Simple setter of the User's birthday.
     * @param birthday to set. It may be any string. But will be better to set
     *                 string in format above. See field declaration.
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Simple getter of the User's total spent cash
     * @return total spent cash in string format
     */
    public String getTotalCash() {
        return totalCash;
    }

    /**
     * Simple setter of the User's total spent cash
     * @param totalCash to set. It may be any string. But will be better to set
     *                  number in string format. These data are displayed in different pages
     *                  and give to user or admin understanding of the situation as a whole.
     *                 Not correct data will confuse them.
     */
    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }

    /**
     * Simple getter of the User's Email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Simple setter of the User's Email.
     * @param email to set. It may be any string. But will be better to set
     *              correct email string. These data are displayed in different pages
     *              and give to user or admin understanding of the situation as a whole.
     *              Not correct data will confuse them.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
