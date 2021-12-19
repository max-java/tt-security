package com.tutrit.tt.security.wiki.repository;

import com.tutrit.tt.security.wiki.bean.Article;
import com.tutrit.tt.security.wiki.config.OwnerOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Override
    @OwnerOnly
    Optional<Article> findById(Long aLong);
}
