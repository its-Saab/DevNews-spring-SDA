package se.sdaproject.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.exceptions.ResourceNotFoundException;
import se.sdaproject.model.Articles;
import se.sdaproject.model.Topics;
import se.sdaproject.repository.ArticlesRepository;
import se.sdaproject.repository.TopicsRepository;

import java.util.List;
import java.util.Set;

@RestController
public class ArticlesController {
    ArticlesRepository articlesRepository;
    TopicsRepository topicsRepository;


    @Autowired
    public ArticlesController(ArticlesRepository articlesRepository, TopicsRepository topicsRepository){
        this.articlesRepository = articlesRepository;
        this.topicsRepository = topicsRepository;
    }

    //Retrieve an article by id
    @GetMapping("/articles/{id}")
    public Articles getArticle(@PathVariable Long id){
        Articles article = articlesRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return article;
    }

    //Create a new article
    @PostMapping("/articles")
    public Articles postArticle(@RequestBody Articles article){
        articlesRepository.save(article);
        return article;
    }

    //Retrieve all articles
    @GetMapping("/articles")
    public List<Articles> getArticlesList(){
        return articlesRepository.findAll();
    }

    //Delete an article by id
    @DeleteMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable Long id){
        Articles article = articlesRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
         articlesRepository.deleteById(id);
    }

    //Update an article by id
    @PutMapping("/articles/{id}")
    public ResponseEntity<Articles> updateArticle(@PathVariable Long id, @RequestBody Articles updatedArticle){
        Articles existingArticle = articlesRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedArticle.setId(id);
        articlesRepository.save(updatedArticle);
        return ResponseEntity.ok(updatedArticle);
    }
    //Get all topics associated with an article:
    @GetMapping("/articles/{articleId}/topics")
    public Set<Topics> getAllTopicsAssociatedWithArticle(@PathVariable Long articleId){
        Articles articles = articlesRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
       return articles.getTopicsList();
    }
    //Post a topic to an article, if the topic doesnt exist it creates it and save it
    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Topics> associatesTopicWithArticle(@PathVariable Long articleId, @RequestBody Topics topicParam){
        Articles article = articlesRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        Boolean doesExist = true;
        if(topicsRepository.findById(topicParam.getId()).isEmpty()){
            topicsRepository.save(topicParam);
            doesExist=false;
        }
        topicParam.getArticlesList().add(article);
        topicsRepository.save(topicParam);
        if(!doesExist){
            return ResponseEntity.status(HttpStatus.CREATED).body(topicParam);
        }else{
            return ResponseEntity.ok(topicParam);
        }
    }

    //Delete the association between an article and a topic while keeping their existence:
    @DeleteMapping("/articles/{articleId}/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long articleId, @PathVariable Long topicId) {
        Articles article = articlesRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        Topics topic = topicsRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        if (topic.getArticlesList().contains(article)) {
            topic.getArticlesList().remove(article);
            topicsRepository.save(topic);
        } else{
            throw new ResourceNotFoundException();
        }
    }


}
