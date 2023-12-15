package ru.simulator.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.simulator.hr.entity.Question;
import ru.simulator.hr.entity.Topic;
import ru.simulator.hr.interfaces.QuestionService;
import ru.simulator.hr.interfaces.TopicService;
import ru.simulator.hr.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TopicService topicService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, TopicService topicService) {
        this.questionRepository = questionRepository;
        this.topicService = topicService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestionsByTopicId(Long topicId) {
        return questionRepository.findByTopicId(topicId);
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestionById(long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        return optionalQuestion.orElse(null);
    }

    @Override
    @Transactional
    public Question createQuestion(Question question, long topicId) {
        Topic topic = topicService.getTopicById(topicId);
        if (topic != null) {
            question.setTopic(topic);
            return questionRepository.save(question);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Question updateQuestion(long id, Question question) {
        Optional<Question> optionalExistingQuestion = questionRepository.findById(id);
        if (optionalExistingQuestion.isPresent()) {
            Question existingQuestion = optionalExistingQuestion.get();
            existingQuestion.setQuestion(question.getQuestion());
            existingQuestion.setCorrect(question.isCorrect());
            existingQuestion.setNote(question.getNote());
            return questionRepository.save(existingQuestion);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteQuestion(long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            questionRepository.delete(optionalQuestion.get());
            return true;
        }
        return false;
    }
}
