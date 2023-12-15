import axios from 'axios'
const BASE_URL = process.env.VUE_APP_BASE_URL;

const CHAPTER_API_URL = `${BASE_URL}/api/chapters`;

//const TOPIC_API_URL = '/api/v1'
//const CHAPTER_API_URL = BACK_URL + '/api/chapters'

class ChapterService {
    getAllChapters() {
        return axios.get(CHAPTER_API_URL);
    }

    getChapterById(id) {
        return axios.get(`${CHAPTER_API_URL}/${id}`);
    }

    createChapter(chapter) {
        return axios.post(CHAPTER_API_URL, chapter);
    }

    updateChapter(id, chapter) {
        return axios.put(`${CHAPTER_API_URL}/${id}`, chapter);
    }

    deleteChapter(id) {
        return axios.delete(`${CHAPTER_API_URL}/${id}`);
    }
}
export default new ChapterService();

