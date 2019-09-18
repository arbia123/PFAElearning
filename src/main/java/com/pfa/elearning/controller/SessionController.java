package com.pfa.elearning.controller;


import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

import com.pfa.elearning.model.Session;
import com.pfa.elearning.model.User;
import com.pfa.elearning.repository.CoursRepository;
import com.pfa.elearning.repository.SessionRepository;
import com.pfa.elearning.repository.UserRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class SessionController {
  
  @Autowired
  private SessionRepository sessionRepository;
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private CoursRepository coursRepository;
  
  
 ///////////////////////////session user/////////////////////////////////////// 

  @GetMapping("/sessions") // Finds all stored lecturers in a pageable format
  public List<Session> getSessions(){
      return  this.sessionRepository.findAll();
  }
  
  @GetMapping("/sessions/{sessionId}")// Finds a lecturer by id (the variable must be wrapped by "{}" and match the @PathVariable name
  public Session getSession(@PathVariable Long sessionId){
      // If the record exists by id return it, otherwise throw an exception
      return this.sessionRepository.findById(sessionId).orElseThrow(
              () -> new NotFoundException("Sesion Not found "+ sessionId)
      );
  }
    
	/*@PostMapping("/users/{userId}/session")
    public Session addSession(@PathVariable Long userId,
                            @Valid @RequestBody Session session) {
        return userRepository.findById(userId)
                .map(user -> {
                    session.setUser((Set<User>) user);
                    return sessionRepository.save(session);
                }).orElseThrow(() -> new NotFoundException("Student not found!"));
    }*/
    
  @PutMapping("/sessions") // Validates the request body as a session type
  public Session updateSession(@Valid @RequestBody Session session){
      // Finds lecturer by id, maps it's content, update new values and save. Throws an exception if not found.
      return this.sessionRepository.findById(session.getId()).map((toUpdate) -> {
          toUpdate.setDate_deb(session.getDate_deb());
          toUpdate.setDate_fin(session.getDate_fin());
          toUpdate.setSujet(session.getSujet());
          return this.sessionRepository.save(toUpdate);
      }).orElseThrow(() -> new NotFoundException("Session Not found "+session.getId()));
  }
    
  @DeleteMapping("sessions/{id}") // Finds lecturer by id
  public ResponseEntity deleteSession(@PathVariable Long id){
      // If id exists, delete the record and return a response message, otherwise throws exception
      return this.sessionRepository.findById(id).map((toDelete) -> {
          this.sessionRepository.delete(toDelete);
          return ResponseEntity.ok("session  id " + id + " deleted");
      }).orElseThrow(() -> new NotFoundException("Session Not found"+ id));
  }
  
  
    
    @PostMapping("/sessions") // Validates the request body as a session type
    public Session createSession(@Valid @RequestBody Session session){
        // Saves and return the new lecturer
        return this.sessionRepository.save(session);
    }
    
    
  @PostMapping("sessions/{id}/users/{userId}") // Path variable names must match with method's signature variables.
    public Set<User> addUser(@PathVariable Long id, @PathVariable Long userId){
        // Finds a persisted student
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User Not found " + userId)
        );
        
        // Finds a session and adds the given user to the session's set.
        return this.sessionRepository.findById(id).map((session) -> {
            session.getUsers().add(user);
            return this.sessionRepository.save(session).getUsers(); 
        }).orElseThrow(() -> new NotFoundException("Session"));
    }

   
   /////////////////////////////// session cours crud  ////////////////////////////////// 
   
   
  
 /*  @GetMapping("/cours/{coursId}/sesssions")
   public List<Session> getContactByCoursId(@PathVariable Long coursId) {
     
       if(!sessionRepository.existsById(coursId)) {
           throw new NotFoundException("Session not found!");
       }
     
     return sessionRepository.findByCoursId(coursId);
   }
   */
   
   @PostMapping("/cours/{coursId}/sessions")
   public Session addSession(@PathVariable Long coursId,
                           @Valid @RequestBody Session session) {
       return coursRepository.findById(coursId)
               .map(cours -> {
                   session.setCours(cours);
                   return sessionRepository.save(session);
               }).orElseThrow(() -> new NotFoundException("Cours not found!"));
   }
   
   @PutMapping("/cours/{coursId}/sessions/{sessionId}")
   public Session updateSession(@PathVariable Long coursId,
                   @PathVariable Long sessionId,
                   @Valid @RequestBody Session sessionUpdated) {
     
     if(!coursRepository.existsById(coursId)) {
       throw new NotFoundException("Session not found!");
     }
     
       return sessionRepository.findById(sessionId)
               .map(session -> {
               	session.setDate_deb(sessionUpdated.getDate_deb());
            	session.setDate_fin(sessionUpdated.getDate_fin());
            	session.setSujet(sessionUpdated.getSujet());
                   return sessionRepository.save(session);
               }).orElseThrow(() -> new NotFoundException("Session not found!"));
   }
   
   @DeleteMapping("/cours/{coursId}/sessions/{sessionId}")
   public String deleteSession(@PathVariable Long coursId,
                    @PathVariable Long sessionId) {
     
     if(!coursRepository.existsById(coursId)) {
       throw new NotFoundException("Cours not found!");
     }
     
       return sessionRepository.findById(sessionId)
               .map(session -> {
                   sessionRepository.delete(session);
                   return "Deleted Successfully!";
               }).orElseThrow(() -> new NotFoundException("Contact not found!"));
   }
}