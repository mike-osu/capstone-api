package edu.oregonstate.capstone.models;

public class LocationMessage {

    private Long experienceId;

    private Double latitude;

    private Double longitude;

    public LocationMessage() {

    }

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocationMessage{" +
                "experienceId=" + experienceId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
