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
import com.pfa.elearning.model.Document;
import com.pfa.elearning.repository.DocumentRepository;
import com.pfa.elearning.repository.SessionRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DocumentController {
  @Autowired
  private DocumentRepository documentRepository;
  
  @Autowired
  private SessionRepository sessionRepository;
  
  
  
  @GetMapping("/sessions/documents")
  public List<Document> getAllDocuments() {
    return documentRepository.findAll();
  }
    @GetMapping("/sessions/{sessionId}/documents")
    public List<Document> getContactBySessionId(@PathVariable Long sessionId) {
      
        if(!sessionRepository.existsById(sessionId)) {
            throw new NotFoundException("Session not found!");
        }
      
      return documentRepository.findBySessionId(sessionId);
    }
    
    @PostMapping("/sessions/{sessionId}/documents")
    public Document addDocument(@PathVariable Long sessionId,
                            @Valid @RequestBody Document document) {
        return sessionRepository.findById(sessionId)
                .map(session -> {
                    document.setSession(session);
                    return documentRepository.save(document);
                }).orElseThrow(() -> new NotFoundException("Session not found!"));
    }
    @PutMapping("/sessions/{sessionId}/documents/{documentId}")
    public Document updateDocument(@PathVariable Long sessionId,
                    @PathVariable Long documentId,
                    @Valid @RequestBody Document documentUpdated) {
      
      if(!sessionRepository.existsById(sessionId)) {
        throw new NotFoundException("Session not found!");
      }
      
        return documentRepository.findById(documentId)
                .map(document -> {
                	document.setDesignation(documentUpdated.getDesignation());
                	document.setTypes(documentUpdated.getType());
                    return documentRepository.save(document);
                }).orElseThrow(() -> new NotFoundException("Document not found!"));
    }
    
    @DeleteMapping("/sessions/{sessionId}/documents/{documentId}")
    public String deleteDocument(@PathVariable Long sessionId,
                     @PathVariable Long documentId) {
      
      if(!sessionRepository.existsById(sessionId)) {
        throw new NotFoundException("Session not found!");
      }
      
        return documentRepository.findById(documentId)
                .map(document -> {
                    documentRepository.delete(document);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Contact not found!"));
    }
}