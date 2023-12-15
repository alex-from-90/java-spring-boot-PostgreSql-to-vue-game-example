import axios from 'axios';

const BASE_URL = process.env.VUE_APP_BASE_URL;
const CHAPTER_API_URL = `${BASE_URL}/api/chapters`;
const TOPIC_API_URL = `${BASE_URL}/api/topics`;
const QUESTION_API_URL = `${BASE_URL}/api/questions`;

class DataService {
    getAllChapters() {
        return axios.get(CHAPTER_API_URL);
    }

    getAllTopics(chapterId) {
        if (chapterId) {
            return axios.get(`${TOPIC_API_URL}?chapterId=${chapterId}`);
        }
        return axios.get(TOPIC_API_URL);
    }

    getAllQuestions(topicId) {
        if (topicId) {
            return axios.get(`${QUESTION_API_URL}?topicId=${topicId}`);
        }
        return axios.get(QUESTION_API_URL);
    }
}

export default new DataService();
