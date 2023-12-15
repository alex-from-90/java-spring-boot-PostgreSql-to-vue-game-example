package ru.simulator.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Импортируем класс транзакций

import ru.simulator.hr.entity.Chapter;
import ru.simulator.hr.interfaces.ChapterService;
import ru.simulator.hr.repository.ChapterRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // Добавляем аннотацию @Transactional для управления транзакциями в этом сервисе
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;

    @Autowired
    public ChapterServiceImpl(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chapter> getAllChapters() {
        return chapterRepository.findAllChaptersWithTopics();
    }

    @Override
    @Transactional(readOnly = true)
    public Chapter getChapterById(long id) {
        Optional<Chapter> optionalChapter = chapterRepository.findById(id);
        return optionalChapter.orElse(null);
    }

    @Override
    @Transactional
    public Chapter createChapter(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Override
    @Transactional
    public Chapter updateChapter(long id, Chapter chapter) {
        Optional<Chapter> optionalExistingChapter = chapterRepository.findById(id);
        if (optionalExistingChapter.isPresent()) {
            Chapter existingChapter = optionalExistingChapter.get();
            existingChapter.setChapterName(chapter.getChapterName());
            return chapterRepository.save(existingChapter);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteChapter(long id) {
        Optional<Chapter> optionalChapter = chapterRepository.findById(id);
        if (optionalChapter.isPresent()) {
            chapterRepository.delete(optionalChapter.get());
            return true;
        }
        return false;
    }
}
