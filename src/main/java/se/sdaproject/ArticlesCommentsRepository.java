package se.sdaproject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlesCommentsRepository extends JpaRepository<ArticlesComments,Long> {
}
