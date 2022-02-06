package com.tutrit.tt.security.wiki.repository;

import com.tutrit.tt.security.wiki.bean.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Override
    Optional<Article> findById(Long aLong);
}
