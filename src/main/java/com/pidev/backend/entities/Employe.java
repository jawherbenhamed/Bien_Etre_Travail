package com.pidev.backend.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employe implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long idEmploye;
	
	private String nom;
	private String prenom;
	
	@OneToMany (mappedBy = "employeCommentaire")
	@JsonIgnore
	private List<Commentaire> commentairesEmploye;
	
	@OneToMany (mappedBy = "employeReact")
	@JsonIgnore
	private List<Reaction> reactionsEmploye;
	
	@OneToMany (mappedBy = "employeAvis")
	@JsonIgnore
	private List<Avis> avisEmploye;
	
	
}
