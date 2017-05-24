package com.theironyard.charlotte;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Addresses {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String street;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String state;

    @Column(nullable = false)
    int zip;

    public Addresses() {
    }

    public Addresses(String street, String city, String state, int zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}
