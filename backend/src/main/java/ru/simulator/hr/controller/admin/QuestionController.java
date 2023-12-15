package ru.simulator.hr.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simulator.hr.entity.Question;
import ru.simulator.hr.interfaces.QuestionService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(@RequestParam(required = false) Long topicId) {
        if (topicId != null) {
            List<Question> questions = questionService.getQuestionsByTopicId(topicId);
            if (!questions.isEmpty()) {
                return new ResponseEntity<>(questions, HttpStatus.OK);
            }
        } else {
            List<Question> questions = questionService.getAllQuestions();
            if (!questions.isEmpty()) {
                return new ResponseEntity<>(questions, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK); //Заглушка
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") long id) {
        Question question = questionService.getQuestionById(id);
        if (question != null) {
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestParam("topicId") long topicId, @RequestBody Question question) {
        Question createdQuestion = questionService.createQuestion(question, topicId);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable("id") long id, @RequestBody Question question) {
        Question updatedQuestion = questionService.updateQuestion(id, question);
        if (updatedQuestion != null) {
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") long id) {
        boolean deleted = questionService.deleteQuestion(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}