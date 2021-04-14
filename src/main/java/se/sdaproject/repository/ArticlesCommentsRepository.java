package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.sdaproject.model.ArticlesComments;

import java.util.List;

@Repository
public interface ArticlesCommentsRepository extends JpaRepository<ArticlesComments,Long> {
    List<ArticlesComments> findByAuthorName(@Param("name") String name);
}
