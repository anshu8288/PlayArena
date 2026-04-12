package com.anshu.PlayArena.arena;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "arena")
public class Arena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // Turf 1

    @Enumerated(EnumType.STRING)
    private SportsType sportsType; // Cricket, Badminton, TT, Tennis...

    public Arena() {
    }

    public Arena(Integer id, String name, SportsType sportsType) {
        this.id = id;
        this.name = name;
        this.sportsType = sportsType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SportsType getSportsType() {
        return sportsType;
    }

    public void setSportsType(SportsType sportsType) {
        this.sportsType = sportsType;
    }

}
