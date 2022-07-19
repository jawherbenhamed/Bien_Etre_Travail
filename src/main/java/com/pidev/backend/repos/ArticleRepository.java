package com.pidev.backend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.pidev.backend.entities.Article;


@Repository
@CrossOrigin("http://localhost:4200/") // pour autoriser angular
public interface ArticleRepository  extends JpaRepository<Article, Long> {

	List<Article> findByTitre(String titre);
	List<Article> findByTitreContains(String titre);
}

