package ru.simulator.hr.interfaces;

import ru.simulator.hr.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestions();

    List<Question> getQuestionsByTopicId(Long topicId);

    Question getQuestionById(long id);

    Question createQuestion(Question question, long topicId);

    Question updateQuestion(long id, Question question);

    boolean deleteQuestion(long id);
}