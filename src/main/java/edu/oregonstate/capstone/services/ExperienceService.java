package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Experience;

import java.util.List;

public interface ExperienceService {

    Experience findById(Long id);

    List<Experience> getAll();

    List<Experience> findByKeyword(String keyword);

    Experience save(Experience experience);

    void delete(Long id);
}
