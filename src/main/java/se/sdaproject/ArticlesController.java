package se.sdaproject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticlesController {
    ArticlesRepository articlesRepository;


    @Autowired
    public ArticlesController(ArticlesRepository articlesRepository){
        this.articlesRepository = articlesRepository;
    }

    @GetMapping("/articles/{id}")
    public Articles getArticle(@PathVariable Long id){
        Articles article = articlesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return article;
    }


    @PostMapping("/articles")
    public Articles postArticle(@RequestBody Articles article){
        articlesRepository.save(article);
        return article;
    }
    @GetMapping("/articles")
    public List<Articles> getArticlesList(){
        return articlesRepository.findAll();
    }

    @DeleteMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable Long id){
        Articles article = articlesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
         articlesRepository.deleteById(id);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Articles> updateArticle(@PathVariable Long id, @RequestBody Articles updatedArticle){
        Articles existingArticle = articlesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        updatedArticle.setId(id);
        articlesRepository.save(updatedArticle);
        return ResponseEntity.ok(updatedArticle);
    }
}
