package com.tutrit.tt.security.wiki.controller;

import com.tutrit.tt.security.custom.Principal;
import com.tutrit.tt.security.custom.SecurityContext;
import com.tutrit.tt.security.wiki.ArticleService;
import com.tutrit.tt.security.wiki.bean.AccessList;
import com.tutrit.tt.security.wiki.bean.Article;
import com.tutrit.tt.security.wiki.config.AdminOnly;
import com.tutrit.tt.security.wiki.repository.AccessListRepository;
import com.tutrit.tt.security.wiki.repository.ArticleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ArticleController {
    ArticleRepository articleRepository;
    AccessListRepository accessListRepository;
    ArticleService articleService;

    @GetMapping("/blog")
    public ModelAndView showBlogPage() {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("blog");
        mov.addObject("articles", articleRepository.findAll());
        return mov;
    }

    @GetMapping("/article/{id}")
    public ModelAndView openArticleInReadMode(@PathVariable Long id) {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("article");
        mov.addObject("article", articleService.findById(id));
        return mov;
    }

    @AdminOnly
    @GetMapping("/article/{id}/edit")
    public ModelAndView openArticleInEditMode(@PathVariable Long id) {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("article_form");
        mov.addObject("article", articleRepository.findById(id).get());
        return mov;
    }

    @AdminOnly
    @PostMapping("/article/{id}/edit")
    public String updateArticle(@ModelAttribute Article article, @PathVariable Long id) {
        articleRepository.save(article);
        return "redirect:/blog";
    }

    @AdminOnly
    @GetMapping("/article/add")
    public ModelAndView openEmptyArticleForm() {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("article_form");
        mov.addObject("article", new Article());
        return mov;
    }

    @AdminOnly
    @PostMapping("/article/add")
    public String savePerson(@ModelAttribute Article article, HttpServletRequest request) {
        Article savedArticle = articleRepository.save(article);
        Principal principal = SecurityContext.currentUser();
        accessListRepository.save(AccessList.builder()
                .articleId(savedArticle.getArticleId())
                .principalId(principal.getId())
                .build());
        return "redirect:/blog";
    }
}
