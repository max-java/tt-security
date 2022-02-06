package com.tutrit.tt.security.wiki.repository;

import com.tutrit.tt.security.wiki.bean.AccessList;
import com.tutrit.tt.security.wiki.bean.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessListRepository extends JpaRepository<AccessList, Long> {
    AccessList findAccessListByArticleId(Long id);
}
