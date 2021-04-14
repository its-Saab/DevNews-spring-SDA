package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.sdaproject.model.Topics;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long> {

}
