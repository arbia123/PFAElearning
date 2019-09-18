package com.pfa.elearning.repository;

import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfa.elearning.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
	List<Session> findByCoursId(Long coursId); 
}