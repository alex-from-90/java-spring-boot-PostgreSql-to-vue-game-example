package ru.simulator.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simulator.hr.entity.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Метод для поиска вопросов по идентификатору темы
    List<Question> findByTopicId(Long topicId);
}
