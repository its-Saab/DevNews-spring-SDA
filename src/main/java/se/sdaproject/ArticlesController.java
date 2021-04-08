package se.sdaproject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticlesController {
    ArticlesRepository articlesRepository;


    @Autowired
    public ArticlesController(ArticlesRepository articlesRepository){
        this.articlesRepository = articlesRepository;
    }

    @GetMapping("/articles/{id}")
    public Articles getArticle(@PathVariable long id){
        Articles article = articlesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return article;
    }


    @PostMapping("/articles")
    public Articles postArticle(@RequestBody Articles article){
        articlesRepository.save(article);
        return article;
    }

}
