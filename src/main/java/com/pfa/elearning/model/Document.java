package com.pfa.elearning.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "document", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "designation"
        }),
        @UniqueConstraint(columnNames = {
            "type"
        })
})
public class Document{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String designation;

    @NotBlank
    @Size(min=3, max = 50)
    private String type;

    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    @JsonIgnore
    private Session session;


	public Document() {
		super();
	}


	public Document(@NotBlank @Size(min = 3, max = 50) String designation,
			@NotBlank @Size(min = 3, max = 50) String type) {
		super();
		this.designation = designation;
		this.type = type;
	}


	public Document(@NotBlank @Size(min = 3, max = 50) String designation,
			@NotBlank @Size(min = 3, max = 50) String type, Session session) {
		super();
		this.designation = designation;
		this.type = type;
		this.session = session;
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


	public String getType() {
		return type;
	}


	public void setTypes(String type) {
		this.type = type;
	}


	public Session getSession() {
		return session;
	}


	public void setSession(Session session) {
		this.session = session;
	}
    
    
    
}
