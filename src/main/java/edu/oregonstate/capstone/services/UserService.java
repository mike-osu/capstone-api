package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.User;

public interface UserService {

    User findById(Long id);

    User findByUsername(String username);

    User save(User user);
}
