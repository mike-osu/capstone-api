package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Experience;

import java.util.List;

public interface ExperienceService {

    Experience findById(Long id);

    List<Experience> getAll();

    Experience save(Experience experience);

    void delete(Long id);
}
