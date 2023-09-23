package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String phoneNumber;

    private String password;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    List<Reservation> reservations = new ArrayList<>();

    public User(int id, String name, String password, List<Reservation> reservations , String phoneNumber) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.reservations = reservations;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
