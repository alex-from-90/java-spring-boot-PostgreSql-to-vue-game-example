import axios from 'axios';
const BASE_URL = process.env.VUE_APP_BASE_URL;

const QUESTION_API_URL = `${BASE_URL}/api/questions`;

class QuestionDataService {
    getAllQuestions(topicId) {
        if (topicId) {
            return axios.get(`${QUESTION_API_URL}?topicId=${topicId}`);
        }
        return axios.get(QUESTION_API_URL);
    }

    getQuestionById(id) {
        return axios.get(`${QUESTION_API_URL}/${id}`);
    }

    createQuestion(topicId, question) {
        return axios.post(`${QUESTION_API_URL}?topicId=${topicId}`, question);
    }


    updateQuestion(id, question) {
        return axios.put(`${QUESTION_API_URL}/${id}`, question);
    }

    deleteQuestion(id) {
        return axios.delete(`${QUESTION_API_URL}/${id}`);
    }
}

export default new QuestionDataService();