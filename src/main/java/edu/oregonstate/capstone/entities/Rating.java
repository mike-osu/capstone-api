package edu.oregonstate.capstone.entities;

import edu.oregonstate.capstone.entities.pk.RatingId;

import javax.persistence.*;

@Entity
@Table(name = "t_rating")
@IdClass(RatingId.class)
public class Rating {

    @Id
    private Long experienceId;

    @Id
    private Long userId;

    @Column(nullable = false)
    private Integer starCount;

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }
}
