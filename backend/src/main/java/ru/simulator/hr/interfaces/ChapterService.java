package ru.simulator.hr.interfaces;

import ru.simulator.hr.entity.Chapter;

import java.util.List;

public interface ChapterService {

    List<Chapter> getAllChapters();

    Chapter getChapterById(long id);

    Chapter createChapter(Chapter chapter);

    Chapter updateChapter(long id, Chapter chapter);

    boolean deleteChapter(long id);
}