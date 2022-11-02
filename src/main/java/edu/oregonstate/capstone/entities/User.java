package edu.oregonstate.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(columnDefinition="text")
    @JsonIgnore
    private String idToken;

    @Column(columnDefinition="text")
    @JsonIgnore
    private String accessToken;

    @JsonIgnore
    @OneToMany(targetEntity = Experience.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Experience> experiences;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private int experienceCount;

    @JsonIgnore
    @OneToMany(targetEntity = Trip.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Trip> trips;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private int tripCount;

    @PreRemove
    public void checkUserAssociationBeforeRemoval() {
        if (!this.experiences.isEmpty()) {
            throw new RuntimeException("Can't remove a user that has experiences.");
        }

        if (!this.trips.isEmpty()) {
            throw new RuntimeException("Can't remove a user that has trips.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public int getExperienceCount() {
        return getExperiences().size();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public int getTripCount() {
        return trips.size();
    }
}
