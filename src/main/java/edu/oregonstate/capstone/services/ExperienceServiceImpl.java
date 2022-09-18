package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.repositories.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    ExperienceRepository experienceRepository;

    @Override
    public Experience findById(Long id) {
        if (experienceRepository.findById(id).isPresent()) {
            return experienceRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<Experience> getAll() {
        return experienceRepository.findAll();
    }

    @Override
    public List<Experience> findByKeyword(String keyword) {
        return experienceRepository.findByKeyword(keyword);
    }

    @Override
    public Experience save(Experience experience) {

        return experienceRepository.save(experience);
    }

    @Override
    public void delete(Long id) {
        experienceRepository.deleteById(id);
    }
}
