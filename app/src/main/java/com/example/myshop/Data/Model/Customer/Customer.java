package com.example.myshop.Data.Model.Customer;

import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("billing")
    private Billing billing;

    @SerializedName("shipping")
    private Shipping shipping;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("email")
    private String email;

    @SerializedName("is_paying_customer")
    private boolean isPayingCustomer;

    public Customer() {
    }

    public Customer(int id, String firstName, String lastName, String username, String email) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setPayingCustomer(boolean payingCustomer) {
        isPayingCustomer = payingCustomer;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setIsPayingCustomer(boolean isPayingCustomer) {
        this.isPayingCustomer = isPayingCustomer;
    }

    public boolean isIsPayingCustomer() {
        return isPayingCustomer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}