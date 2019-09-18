package com.pfa.elearning.controller;

import java.util.List;

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
import com.pfa.elearning.model.Cours;
import com.pfa.elearning.repository.CoursRepository;
import com.pfa.elearning.repository.UserRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CoursController {
  @Autowired
  private CoursRepository coursRepository;
  
  @Autowired
  private UserRepository userRepository;
  
  
  @GetMapping("/cours")
  public List<Cours> getAllCours() {
    return coursRepository.findAll();
  }
    @GetMapping("/users/{userId}/cours")
    public List<Cours> getCoursByUserId(@PathVariable Long userId) {
      
        if(!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found!");
        }
      
      return coursRepository.findByUserId(userId);
    }
    
    @PostMapping("/users/{userId}/cours")
    public Cours addCours(@PathVariable Long userId,
                            @Valid @RequestBody Cours cours) {
        return userRepository.findById(userId)
                .map(user -> {
                    cours.setUser(user);
                    return coursRepository.save(cours);
                }).orElseThrow(() -> new NotFoundException("Student not found!"));
    }
    
    @PutMapping("/users/{userId}/cours/{coursId}")
    public Cours updateCours(@PathVariable Long userId,
                    @PathVariable Long coursId,
                    @Valid @RequestBody Cours coursUpdated) {
      
      if(!userRepository.existsById(userId)) {
        throw new NotFoundException("User not found!");
      }
      
      return coursRepository.findById(coursId)
              .map(cours -> {
                  cours.setDesignation(coursUpdated.getDesignation());
                  cours.setDescription(coursUpdated.getDescription());
                  return coursRepository.save(cours);
              }).orElseThrow(() -> new NotFoundException("Cours not found!"));
  }
    
    @DeleteMapping("/users/{userId}/cours/{coursId}")
    public String deleteCours(@PathVariable Long userId,
                     @PathVariable Long coursId) {
      
      if(!userRepository.existsById(userId)) {
        throw new NotFoundException("Student not found!");
      }
      
        return coursRepository.findById(coursId)
                .map(cours -> {
                    coursRepository.delete(cours);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Contact not found!"));
    }
}