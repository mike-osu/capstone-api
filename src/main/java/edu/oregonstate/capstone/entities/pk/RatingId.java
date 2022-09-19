package edu.oregonstate.capstone.entities.pk;

import java.io.Serializable;
import java.util.Objects;

public class RatingId implements Serializable {

    private Long experienceId;

    private Long userId;

    public RatingId() {
    }

    public RatingId(Long experienceId, Long userId) {
        this.experienceId = experienceId;
        this.userId = userId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingId ratingId = (RatingId) o;

        if (!Objects.equals(experienceId, ratingId.experienceId))
            return false;
        return Objects.equals(userId, ratingId.userId);
    }

    @Override
    public int hashCode() {
        int result = experienceId != null ? experienceId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
