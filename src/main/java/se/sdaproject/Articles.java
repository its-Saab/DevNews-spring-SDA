package se.sdaproject;


import javax.persistence.*;
import java.util.List;

@Entity
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;

    private String authorName;

    @OneToMany(mappedBy = "commentedArticle")
    private List<ArticlesComments> articleCommentsList;


    public Articles(){}

    public Articles(String title, String body, String authorName) {
        this.title = title;
        this.body = body;
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void updateArticle(Articles updatedArticle){
        this.title = updatedArticle.title;
        this.authorName=updatedArticle.authorName;
        this.body=updatedArticle.body;
    }

    public List<ArticlesComments> getArticleCommentsList() {
        return articleCommentsList;
    }


    public void setArticleCommentsList(List<ArticlesComments> articleCommentsList) {
        this.articleCommentsList = articleCommentsList;
    }
}
