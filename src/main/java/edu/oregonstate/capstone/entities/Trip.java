package edu.oregonstate.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_trip")
public class Trip extends BaseEntity {

    private String name;

    @Column(columnDefinition="text")
    private String description;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private String username;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany
    @JoinTable(
            name = "trips_experiences",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "experience_id"))
    private Set<Experience> experiences = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(Set<Experience> experiences) {
        this.experiences = experiences;
    }
}
