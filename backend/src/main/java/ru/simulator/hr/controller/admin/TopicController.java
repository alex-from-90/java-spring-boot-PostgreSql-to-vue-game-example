package ru.simulator.hr.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simulator.hr.entity.Topic;
import ru.simulator.hr.interfaces.TopicService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics(@RequestParam(required = false) Long chapterId) {
        if (chapterId != null) {
            List<Topic> topics = topicService.getTopicsByChapterId(chapterId);
            if (!topics.isEmpty()) {
                return new ResponseEntity<>(topics, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.OK); // Заглушка.
            }
        } else {
            List<Topic> topics = topicService.getAllTopics();
            if (!topics.isEmpty()) {
                return new ResponseEntity<>(topics, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);             }
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable("id") long id) {
        Topic topic = topicService.getTopicById(id);
        if (topic != null) {
            return new ResponseEntity<>(topic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic, @RequestParam(required = false) Long chapterId) {
        if (chapterId != null) {
            Topic createdTopic = topicService.createTopic(topic, chapterId);
            return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);    }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable("id") long id, @RequestBody Topic topic) {
        Topic updatedTopic = topicService.updateTopic(id, topic);
        if (updatedTopic != null) {
            return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("id") long id) {
        boolean deleted = topicService.deleteTopic(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}