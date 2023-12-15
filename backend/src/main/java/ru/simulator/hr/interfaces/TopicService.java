package ru.simulator.hr.interfaces;

import ru.simulator.hr.entity.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> getAllTopics();

    List<Topic> getTopicsByChapterId(Long chapterId);

    Topic getTopicById(long id);

    Topic createTopic(Topic topic, long chapterId);

    Topic updateTopic(long id, Topic topic);

    boolean deleteTopic(long id);
}