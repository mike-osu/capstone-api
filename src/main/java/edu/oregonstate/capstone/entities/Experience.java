package edu.oregonstate.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_experience")
public class Experience extends BaseEntity {

    private String title;

    @Column(columnDefinition="text")
    private String description;

    private String city;

    private String state;

    private String country;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private String username;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Formula("(select coalesce(avg(r.star_count), 0.0) from t_rating r where r.experience_id = id)")
    private double averageRating;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imageUrl;

    @JsonIgnore
    @ManyToMany(mappedBy = "experiences")
    private Set<Trip> trips= new HashSet<>();

    @PreRemove
    public void checkExpAssociationBeforeRemoval() {
        if (!this.trips.isEmpty()) {
            throw new RuntimeException("Can't remove an experience that is include in a trip.");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }
}
