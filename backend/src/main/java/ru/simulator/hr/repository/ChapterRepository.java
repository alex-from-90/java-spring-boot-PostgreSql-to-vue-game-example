package ru.simulator.hr.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.simulator.hr.entity.Chapter;

import java.util.List;

// Определение интерфейса репозитория для сущности Chapter с использованием Spring Data JPA
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    // Указание на необходимость загрузки связанных сущностей topics при запросе
    @EntityGraph(attributePaths = {"topics"})
    @Query("SELECT c FROM Chapter c")
    List<Chapter> findAllChaptersWithTopics(); // Метод для поиска всех глав с темами
}
