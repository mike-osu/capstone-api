package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByUsername(String username);

    List<User> getAll();

    User save(User user);

    void delete(Long id);
}
