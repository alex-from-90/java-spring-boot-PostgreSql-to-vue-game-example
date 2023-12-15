package ru.simulator.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simulator.hr.entity.Topic;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    // Метод для поиска тем по идентификатору главы
    List<Topic> findByChapterId(Long chapterId);
}
