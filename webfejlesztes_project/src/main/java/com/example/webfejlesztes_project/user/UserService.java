package com.example.webfejlesztes_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) repository.findAll();
    }

    public UserService(UserRepository repository){
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void saveUser(User user) {
        String encoded = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        repository.save(user);
    }

    public User getUserId(Integer id) throws UserNotFoundException {
        Optional<User> result = repository.findById(id);
        if(result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find User" + id);
    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        Long count = repository.countById(id);
        if(count == null || count == 0) {
            throw new UserNotFoundException("Could not find User" + id);
        }
        repository.deleteById(id);
    }
}
