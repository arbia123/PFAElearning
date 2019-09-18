package com.pfa.elearning.model;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "session", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "date_deb"
        }),
        @UniqueConstraint(columnNames = {
            "date_fin"
        }),
        @UniqueConstraint(columnNames = {
                "sujet"
            })
        
        
})
public class Session{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String date_deb;

    @NotBlank
    @Size(min=3, max = 50)
    private String date_fin;

    @NotBlank
    @Size(max = 50)
    private String sujet;

   
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "session_user",
            joinColumns = {@JoinColumn(name = "session_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<User> users = new HashSet<>();
    
   
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cours_id", nullable = false)
    @JsonIgnore
    private Cours cours;
    

    
   @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<Document> document;
   

	public Session() {
		super();
	}

	public Session( String date_deb,String date_fin, String sujet,Set<User> user ) {
		super();
		this.date_deb = date_deb;
		this.date_fin = date_fin;
		this.sujet = sujet;
		/*this.cours = cours;
		this.document = document;*/
	}

	public Session(String date_deb,String date_fin, String sujet  ) {
		super();
		this.date_deb = date_deb;
		this.date_fin = date_fin;
		this.sujet = sujet;
		
	}
	
	
	

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate_deb() {
		return date_deb;
	}

	public void setDate_deb(String date_deb) {
		this.date_deb = date_deb;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public Set<Document> getDocument() {
		return document;
	}

	public void setDocument(Set<Document> document) {
		this.document = document;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	
	

	
    
    
    
    
}