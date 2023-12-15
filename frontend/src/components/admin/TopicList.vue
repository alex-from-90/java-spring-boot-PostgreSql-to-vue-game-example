<template>
  <div class="container">
    <h3>{{ chapterTitle }}</h3>
    <div v-if="message" class="alert alert-success">{{ message }}</div>

    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Название топика</th>
          <th>Редактировать</th>
          <th>Удалить</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="topic in topics" :key="topic.id">
          <td>
            <button class="btn btn-info" @click="viewQuestions(chapterId, topic.id)">
              {{ topic.topicName }}
            </button>
          </td>
          <td>
            <button class="btn btn-warning" @click="updateTopic(topic.id)">
              Редактировать
            </button>
          </td>
          <td>
            <button class="btn btn-danger" @click="deleteTopic(topic.id)">
              Удалить
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-if="topics.length === 0">
      <h1>В теме нет топиков</h1>
    </div>
    <div class="row">
      <div class="col-md-6">
        <button class="btn btn-success" @click="addTopic()">Добавить</button>
      </div>
    </div>
    <button class="btn btn-secondary" @click="goBack">Назад к темам</button>
  </div>
</template>

<script>
import TopicService from '@/service/admin/TopicDataService';

export default {
  name: 'TopicList',
  data() {
    return {
      topics: [],
      chapterId: null,
      chapterTitle: '',
      message: '',
    };
  },
  methods: {
    loadTopics() {
      const params = new URLSearchParams(window.location.search);
      this.chapterId = params.get('chapterId');
      this.chapterTitle = params.get('chapterTitle');
      TopicService.getAllTopics(this.chapterId)
          .then((response) => {
            this.topics = response.data;
          })
          .catch((error) => {
            console.error(error);
          });
    },

    addTopic() {
      this.$router.push({
        name: 'AddTopic',
        query: { chapterId: this.chapterId, mode: 'add' }
      });
    },

    updateTopic(topicId) {
      this.$router.push(`/add-topic?id=${topicId}&chapterId=${this.chapterId}&chapterTitle=${this.chapterTitle}&mode=update`);
    },
    deleteTopic(topicId) {
      TopicService.deleteTopic(topicId)
          .then(() => {
            // Логика обновления списка топиков после удаления
            this.loadTopics();
          })
          .catch((error) => {
            console.error(error);
          });
    },
    // Ваш метод для перехода к списку вопросов
    viewQuestions(chapterId, topicId) {
      this.$router.push({ path: '/questions', query: { chapterId: chapterId, topicId: topicId } });
    },

    goBack() {
      this.$router.push('/chapters');
    },
  },
  mounted() {
    this.loadTopics();
  },
};
</script>

<style>
/* Стили, если требуется */
</style>