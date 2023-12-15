<template>
  <div class="container">
    <h3>{{ mode === 'edit' ? 'Редактирование вопроса' : 'Добавление нового вопроса' }} в топике: {{ topicName }}</h3>

    <div class="form-group">
      <label for="questionInput">Вопрос:</label>
      <textarea id="questionInput" class="form-control" v-model="questionText"></textarea>
    </div>
    <div class="form-group">
      <label>Правильный ответ:</label>
      <div class="answer-buttons">
        <button class="btn btn-primary" :class="{ 'btn-active': correctAnswer === true }" @click="setCorrectAnswer(true)">True</button>
        <button class="btn btn-danger" :class="{ 'btn-active': correctAnswer === false }" @click="setCorrectAnswer(false)">False</button>
      </div>
    </div>
    <div class="form-group">
      <label for="noteInput">Примечание:</label>
      <textarea id="noteInput" class="form-control" v-model="noteText"></textarea>
    </div>
    <div class="form-group">
      <button class="btn btn-success" @click="submitQuestion">{{ mode === 'edit' ? 'Сохранить' : 'Добавить' }}</button>
      <button class="btn btn-danger" @click="goBack">Назад</button>
    </div>
  </div>
</template>

<script>
import QuestionDataService from '@/service/admin/QuestionDataService';

export default {
  name: 'QuestionForm',
  data() {
    return {
      mode: '',
      questionId: null,
      topicId: null,
      topicName: '',
      questionText: '',
      correctAnswer: null,
      noteText: '',
    };
  },
  methods: {
    loadQuestion() {
      if (this.mode === 'edit' && this.questionId) {
        QuestionDataService.getQuestionById(this.questionId)
            .then((response) => {
              const question = response.data;
              this.topicId = question.topic.id;
              this.topicName = question.topic.topicName;
              this.questionText = question.question;
              this.correctAnswer = question.correct;
              this.noteText = question.note || '';
            })
            .catch((error) => {
              console.error('Ошибка при загрузке вопроса:', error);
            });
      }
    },
    submitQuestion() {
      if (this.mode === 'add') {
        const newQuestion = {
          topicId: this.topicId,
          question: this.questionText,
          correct: this.correctAnswer,
          note: this.noteText, // Передача значения примечания
        };

        QuestionDataService.createQuestion(this.topicId, newQuestion)
            .then(() => {
              this.goBack();
            })
            .catch((error) => {
              console.error('Ошибка при добавлении вопроса:', error);
            });
      } else if (this.mode === 'edit') {
        const updatedQuestion = {
          id: this.questionId,
          topicId: this.topicId,
          question: this.questionText,
          correct: this.correctAnswer,
          note: this.noteText, // Передача значения примечания
        };

        QuestionDataService.updateQuestion(this.questionId, updatedQuestion)
            .then(() => {
              this.goBack();
            })
            .catch((error) => {
              console.error('Ошибка при обновлении вопроса:', error);
            });
      }
    },
    goBack() {
      this.$router.push({name: 'TopicQuestions', query: {chapterId: this.chapterId, topicId: this.topicId}});
    },
    setCorrectAnswer(value) {
      this.correctAnswer = value;
    },
  },
  mounted() {
    this.mode = this.$route.query.mode || '';
    this.questionId = this.$route.query.id || null;
    this.topicId = this.$route.query.topicId || null;
    this.topicName = this.$route.query.topicName || '';

    if (this.mode === 'edit' && this.questionId) {
      this.loadQuestion();
    } else if (this.mode === 'add') {
      this.correctAnswer = false; // Или true, в зависимости от логики
    }
  },
};
</script>

<style>
/* Добавление стилей для выделения кнопок, промежутка и обводки */
.answer-buttons {
  display: flex;
  gap: 20px; /* Промежуток между кнопками */
}

.btn.btn-primary.btn-active,
.btn.btn-danger.btn-active {
  border: 4px solid mediumseagreen; /* Обводка кнопок */
}
</style>