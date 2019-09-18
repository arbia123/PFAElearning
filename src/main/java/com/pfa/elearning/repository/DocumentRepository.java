package com.pfa.elearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfa.elearning.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
	List<Document> findBySessionId(Long sessionId);  
	}
