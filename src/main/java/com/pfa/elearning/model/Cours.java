package com.pfa.elearning.model;




import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cours", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "designation"
        }),
        @UniqueConstraint(columnNames = {
            "description"
        })
})
public class Cours{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String designation;

    @NotBlank
    @Size(min=3, max = 50)
    private String description;

    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    
    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Session> session;
    
    
    
    
	public Cours() {
		super();
	}


	public Cours( String designation, String description) {
		super();
		this.designation = designation;
		this.description = description;
	}


	public Cours( String designation, String description, User user , Set<Session> session) {
		super();
		this.designation = designation;
		this.description = description;
		this.user = user;
		//this.session=session;
	}

	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Set<Session> getSession() {
		return session;
	}


	public void setSession(Set<Session> session) {
		this.session = session;
	}


	
	
    
    
}