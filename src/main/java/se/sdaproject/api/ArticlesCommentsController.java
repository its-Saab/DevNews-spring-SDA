package se.sdaproject.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.exceptions.ResourceNotFoundException;
import se.sdaproject.model.Articles;
import se.sdaproject.model.ArticlesComments;
import se.sdaproject.repository.ArticlesCommentsRepository;
import se.sdaproject.repository.ArticlesRepository;

import java.util.List;

@RestController
public class ArticlesCommentsController {
    ArticlesCommentsRepository articlesCommentsRepository;
    ArticlesRepository articlesRepository;

    @Autowired
    public ArticlesCommentsController(ArticlesCommentsRepository articlesCommentsRepository, ArticlesRepository articlesRepository){
        this.articlesRepository = articlesRepository;
        this.articlesCommentsRepository = articlesCommentsRepository;
    }

    //Create a new comment:
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<ArticlesComments> createComment(@PathVariable Long articleId, @Validated @RequestBody ArticlesComments articlesComments){
        Articles article = articlesRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        articlesComments.setCommentedArticle(article);
        articlesCommentsRepository.save(articlesComments);
        return ResponseEntity.status(HttpStatus.CREATED).body(articlesComments);

    }

    //Get article's comments list
    @GetMapping("/articles/{articleId}/comments")
    public List<ArticlesComments> getArticlesCommentsList(@PathVariable Long articleId){
      Articles article = articlesRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
      return article.getArticleCommentsList();
    }

    //Delete a comment using Id
    @DeleteMapping("/comments/{id}")
    public String deleteCommentById(@PathVariable Long id){
        articlesCommentsRepository.deleteById(id);
        return "Comment deleted successfully";
    }

    //Update a comment using Id
    @PutMapping("/comments/{id}")
    public ResponseEntity<ArticlesComments> updateComment(@PathVariable Long id,@Validated @RequestBody ArticlesComments commentParams){
        ArticlesComments existingComment = articlesCommentsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        commentParams.setId(id);
        articlesCommentsRepository.save(commentParams);
        return ResponseEntity.ok(commentParams);
    }

    //Get all comments made by an author
    @GetMapping(value = "/comments", params = {"authorName"})
    public ResponseEntity<List<ArticlesComments>> viewAllCommentsByAuthor(@RequestParam String authorName) {
        return ResponseEntity.ok(articlesCommentsRepository.findByAuthorName(authorName));
    }


}
