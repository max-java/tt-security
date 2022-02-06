package com.tutrit.tt.security.wiki;

import com.tutrit.tt.security.wiki.bean.Article;
import com.tutrit.tt.security.wiki.repository.ArticleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ArticleService {
    ArticleRepository articleRepository;

    public Article findById(long id) {
        return articleRepository.findById(id).get();
    }
}
