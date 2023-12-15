<template>
  <div class="container">
    <h3>{{ chapterTitle }}</h3>
    <div v-if="message" class="alert alert-success">{{ message }}</div>

    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Название топика</th>

        </tr>
        </thead>
        <tbody>
        <tr v-for="topic in topics" :key="topic.id">
          <td>
            <button class="btn btn-info btn-lg btn-block" @click="viewQuestions(chapterId, topic.id)">
              {{ topic.topicName }}
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

    </div>
    <button class="btn btn-secondary" @click="goBack">Назад к темам</button>
  </div>
</template>

<script>
import TopicService from '@/service/admin/TopicDataService';

export default {
  name: 'GameTopics',
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

    viewQuestions(chapterId, topicId) {
      this.$router.push({ path: '/gamequestions', query: { chapterId: chapterId, topicId: topicId } });
    },

    goBack() {
      this.$router.push('/game');
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