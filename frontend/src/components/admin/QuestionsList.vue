<template>
  <div class="container">
    <h3>Вопросы из топика: {{ topicName }}</h3>
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Вопросы топика</th>
          <th>Правда \ Ложь</th>
          <th>Update</th>
          <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="allQuestion in questions" :key="allQuestion.id">
          <td>
            <div>
              {{ allQuestion.question }}
            </div>
            <div v-if="allQuestion.note">
              <button class="btn btn-info btn-sm mt-2" @click="toggleSpoiler(allQuestion)">
                {{ allQuestion.showSpoiler ? 'Скрыть примечание' : 'Показать примечание' }}
              </button>
              <div v-if="allQuestion.showSpoiler">
                <div class="spoiler" style="background-color: #eee; padding: 5px; margin-top: 5px;">
                  <p v-if="allQuestion.note">Примечание: {{ allQuestion.note }}</p>
                  <p v-else>Нет примечания</p>
                </div>
              </div>
            </div>
          </td>
          <td>{{ allQuestion.correct ? 'Правда' : 'Ложь' }}</td>
          <td><button class="btn btn-primary" @click="editQuestion(allQuestion.id)">Редактировать</button></td>
          <td><button class="btn btn-danger" @click="deleteQuestion(allQuestion.id)">Удалить</button></td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="row">
      <div class="col-md-6">
        <button class="btn btn-success" @click="addQuestion()">Добавить</button>
      </div>
    </div>
    <button class="btn btn-danger" @click="goBack">Назад</button>
  </div>
</template>

<script>
import QuestionDataService from '@/service/admin/QuestionDataService';

export default {
  name: 'TopicQuestions',
  data() {
    return {
      topicName: '',
      questions: [],
      chapterId: null,
      topicId: null,
    };
  },
  methods: {
    loadQuestions() {
      this.chapterId = this.$route.query.chapterId;
      this.topicId = this.$route.query.topicId;

      // Загрузка вопросов
      QuestionDataService.getAllQuestions(this.topicId)
          .then((response) => {
            if (response.data && Array.isArray(response.data) && response.data.length > 0) {
              this.questions = response.data.map((question) => ({
                ...question,
                showSpoiler: false,
              }));

              const firstQuestion = response.data[0];
              this.topicName = firstQuestion && firstQuestion.topic ? firstQuestion.topic.topicName : '';
            } else {
              this.questions = []; // Очищаем вопросы, если ответ пустой
              this.topicName = ''; // Сбрасываем имя топика
              console.warn('Данные о вопросах отсутствуют или имеют неверный формат');
              // Можно добавить дополнительную обработку для пустого ответа
            }
          })
          .catch((error) => {
            console.error('Ошибка при загрузке вопросов:', error);
          });


    },
    addQuestion() {
      this.$router.push({path: '/question', query: {chapterId: this.chapterId, topicId: this.topicId, mode: 'add'}});
    },
    editQuestion(questionId) {
      this.$router.push({
        path: '/question',
        query: {id: questionId, chapterId: this.chapterId, topicId: this.topicId, mode: 'edit'}
      });
    },
    deleteQuestion(questionId) {
      QuestionDataService.deleteQuestion(questionId)
          .then(() => {
            console.log('Вопрос успешно удален');
            this.loadQuestions();
          })
          .catch((error) => {
            console.error('Ошибка при удалении вопроса:', error);
          });
    },
    goBack() {
      this.$router.push({name: 'TopicList', query: {chapterId: this.chapterId}});
    },
    toggleSpoiler(question) {
      question.showSpoiler = !question.showSpoiler;
    },
  },
  created() {
    this.loadQuestions();
  },
};
</script>
