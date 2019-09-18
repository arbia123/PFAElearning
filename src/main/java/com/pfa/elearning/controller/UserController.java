package com.pfa.elearning.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfa.elearning.exception.NotFoundException;
import com.pfa.elearning.model.User;
import com.pfa.elearning.repository.UserRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {
  
  @Autowired
  private UserRepository userRepository;
  
    @GetMapping("/user/users")
    public List<User> getAllUser() {
      return userRepository.findAll();
    }
    
    @GetMapping("/user/users/{id}")
    public User getUserByID(@PathVariable Long id) {
      Optional<User> optUser = userRepository.findById(id);
      if(optUser.isPresent()) {
        return optUser.get();
      }else {
        throw new NotFoundException("User not found with id " + id);
      }
    }
    
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }
    
    @PutMapping("/user/users/{id}")
    public User updateUser(@PathVariable Long id,
                                   @Valid @RequestBody User userUpdated) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userUpdated.getName());
                    user.setUsername(userUpdated.getUsername());
                    user.setEmail(userUpdated.getEmail());
                    user.setPassword(userUpdated.getPassword());

                    return userRepository.save(user);
                }).orElseThrow(() -> new NotFoundException("User not found with id " + id));
    }
    
    @DeleteMapping("/user/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return "Delete Successfully!";
                }).orElseThrow(() -> new NotFoundException("User not found with id " + id));
    }
}