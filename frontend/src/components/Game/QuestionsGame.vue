<template>
  <div class="container">
    <div v-if="currentQuestion">
      <h4 v-html="formattedQuestion(currentQuestion.question)"></h4>
      <div class="mt-3">
        <button class="btn btn-success btn-lg mr-3" @click="answerQuestion(true)">Да</button>
        <button class="btn btn-danger btn-lg" @click="answerQuestion(false)">Нет</button>
      </div>
      <div>
        <button class="btn btn-secondary mt-3" @click="goBack">Закончить</button>
      </div>
      <p>Ваш ответ: {{ currentAnswer }}</p>
      <p>Правильный ответ: {{ currentQuestion.correct ? 'Да' : 'Нет' }}</p>
    </div>
    <p>Правильных ответов: {{ correctAnswersCount }}</p>
    <p>Неправильных ответов: {{ wrongAnswersCount }}</p>
    <p v-if="answeredAllQuestions">Правильно ответили на {{ calculatePercentage() }}% вопросов</p>

    <!-- Блок с кнопкой "Назад" -->
    <div>
      <button v-if="answeredAllQuestions" class="btn btn-danger mt-3" @click="goBack">Назад</button>
    </div>

    <!-- Блок с кнопкой "Показать неправильные ответы" -->
    <div v-if="answeredAllQuestions && !showIncorrectAnswers">
      <button class="btn btn-info mt-3" @click="showIncorrect">Показать неправильные ответы</button>
    </div>

    <!-- Блок неправильных ответов -->
    <div v-if="showIncorrectAnswers" class="mt-3">
      <button class="btn btn-info" @click="toggleShowIncorrectAnswers">Скрыть неправильные ответы</button>
      <div v-for="(answer, index) in incorrectAnswers" :key="index" class="card mt-2">
        <div class="card-body">
          <p>{{ answer.question }}</p>
          <p :class="{ 'note-background': answer.note }" v-if="answer.note">Примечание: {{ answer.note }}</p>
        </div>
      </div>
    </div>
  </div>
</template>



<script>
import QuestionDataService from '@/service/admin/QuestionDataService';

export default {
  name: 'GameQuestions',
  data() {
    return {
      topicName: '',
      questions: [],
      currentQuestion: null,
      currentAnswer: null,
      chapterId: null,
      topicId: null,
      correctAnswersCount: 0,
      wrongAnswersCount: 0,
      answeredAllQuestions: false,
      note: null,
      incorrectAnswers: [],
      showIncorrectAnswers: false,
    };
  },
  methods: {
    loadQuestions() {
      this.chapterId = this.$route.query.chapterId;
      this.topicId = this.$route.query.topicId;

      QuestionDataService.getAllQuestions(this.topicId)
          .then((response) => {
            this.questions = this.shuffleQuestions(response.data);
            this.getNextQuestion();
            // Вывод содержимого объекта вопроса в консоль
            console.log(this.questions);
          })
          .catch((error) => {
            console.error('Ошибка при загрузке вопросов:', error);
          });
    },
    shuffleQuestions(questions) {
      return questions.sort(() => Math.random() - 0.5);
    },
    getNextQuestion() {
      if (this.questions.length > 0) {
        this.currentQuestion = this.questions.pop();
      } else {
        this.currentQuestion = null; // Все вопросы были показаны
        this.answeredAllQuestions = true;
      }
    },
    answerQuestion(answer) {
      if (this.currentQuestion.correct === answer) {
        this.currentAnswer = 'Да';
        this.correctAnswersCount++;
      } else {
        this.currentAnswer = 'Нет';
        this.wrongAnswersCount++;
        this.incorrectAnswers.push({
          question: this.currentQuestion.question,
          note: this.currentQuestion.note || null, // добавляем note в массив неправильных ответов
        });
      }
      this.getNextQuestion();
    },
    goBack() {
      this.$router.push({name: 'GameTopics', query: {chapterId: this.chapterId}});
    },
   /* временно не используется
      finishQuiz() {
      this.answeredAllQuestions = true;
    },
    */
    formattedQuestion(question) {
      // Пример форматирования текста внутри тега <p> для улучшения читаемости
      return `<p style="font-family: Arial, sans-serif; line-height: 1.6;">${question}</p>`;
    },
    calculatePercentage() {
      const totalQuestions = this.correctAnswersCount + this.wrongAnswersCount;
      if (totalQuestions === 0) {
        return 0;
      }
      return Math.round((this.correctAnswersCount / totalQuestions) * 100);
    },
    toggleShowIncorrectAnswers() {
      this.showIncorrectAnswers = !this.showIncorrectAnswers;
    },
    showIncorrect() {
      this.showIncorrectAnswers = true;
    },
  },
  created() {
    this.loadQuestions();
  },
};
</script>
<style>
/* Добавляем стили для note-background */
.note-background {
  background-color: #eee;
  padding: 5px;
  border-radius: 5px;
}
</style>