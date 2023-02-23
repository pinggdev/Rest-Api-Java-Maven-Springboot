package com.restapi.Models;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
public class Absen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "jam_absen")
    private ZonedDateTime jamAbsen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ZonedDateTime getJamAbsen() {
        return jamAbsen;
    }

    public void setJamAbsen(ZonedDateTime jamAbsen) {
        this.jamAbsen = jamAbsen;
    }
}
