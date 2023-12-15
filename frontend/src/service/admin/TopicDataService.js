import axios from 'axios';

const BASE_URL = process.env.VUE_APP_BASE_URL;

const TOPIC_API_URL = `${BASE_URL}/api/topics`;
class TopicService {
    getAllTopics(chapterId) {
        if (chapterId) {
            return axios.get(`${TOPIC_API_URL}?chapterId=${chapterId}`);
        }
        return axios.get(TOPIC_API_URL);
    }

    getTopicById(id) {
        return axios.get(`${TOPIC_API_URL}/${id}`);
    }

    createTopic(topic, chapterId) {
        const data = {
            ...topic
        };

        return axios.post(`${TOPIC_API_URL}?chapterId=${chapterId}`, data);
    }

    updateTopic(id, topic) {
        return axios.put(`${TOPIC_API_URL}/${id}`, topic);
    }

    deleteTopic(id) {
        return axios.delete(`${TOPIC_API_URL}/${id}`);
    }
}

export default new TopicService();
