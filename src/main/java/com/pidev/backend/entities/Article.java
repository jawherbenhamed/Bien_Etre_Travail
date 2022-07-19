package com.pidev.backend.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long idArticle;
	
	private String type;
	private String description;
	private String titre;
	private String imageArticle;
	private Date dateCreation;
	
	@OneToMany(mappedBy = "article")
	private List<Commentaire> listCommentaireArticle;
	
	@OneToMany(mappedBy = "articleReact")
	private List<Reaction> listReactionArticle;
	
	
	
}
