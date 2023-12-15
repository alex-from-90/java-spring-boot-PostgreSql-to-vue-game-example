<template>
  <div>
    <h3>{{ mode === 'add' ? 'Добавление топика' : 'Редактирование топика' }}</h3>
    <div class="container">
      <form @submit.prevent="validateAndSubmit">
        <fieldset class="form-group">
          <label>Название топика</label>
          <input type="text" class="form-control" v-model="topicName" />
        </fieldset>
        <button class="btn btn-success" type="submit">{{ mode === 'add' ? 'Добавить' : 'Сохранить' }}</button>
      </form>
      <button class="btn btn-danger" @click="goBack">
        Назад
      </button>
    </div>
  </div>
</template>

<script>
import TopicService from '@/service/admin/TopicDataService';

export default {
  name: 'AddTopic',
  data() {
    return {
      topicName: '',
      errors: [],
      mode: '',
      topicId: null,
      chapterId: null,
      chapterTitle: '', // Добавляем переменную для хранения названия главы
    };
  },
  methods: {
    validateAndSubmit() {
      this.errors = [];
      if (this.topicName === '') {
        this.errors.push('Введите название топика');
      }
      if (this.errors.length === 0) {
        const topic = {
          topicName: this.topicName,
          chapterId: this.chapterId,
        };
        if (this.mode === 'add') {
          TopicService.createTopic(topic, this.chapterId) // Передаем chapterId в метод createTopic
              .then(() => {
                this.$router.push({ name: 'TopicList', query: { chapterId: this.chapterId } });
              })
              .catch((error) => {
                console.log(error);
              });
        } else if (this.mode === 'update' && this.topicId) {
          TopicService.updateTopic(this.topicId, topic)
              .then(() => {
                this.$router.push(`/topics?chapterId=${this.chapterId}`);
              })
              .catch((error) => {
                console.log(error);
              });
        }
      }
    },
    goBack() {
      this.$router.push({ name: 'TopicList', query: { chapterId: this.chapterId } });
    },

    setMode() {
      const params = new URLSearchParams(window.location.search);
      this.mode = params.get('mode');
      this.topicId = params.get('id');
      this.chapterId = params.get('chapterId'); // Получаем id главы из параметров запроса
      this.chapterTitle = params.get('chapterTitle'); // Получаем название главы из параметров запроса
    },
    getTopicData() {
      TopicService.getTopicById(this.topicId)
          .then((response) => {
            this.topicName = response.data.topicName;
          })
          .catch((error) => {
            console.error(error);
          });
    },
  },
  mounted() {
    const routeParams = this.$route.params;
    this.chapterId = routeParams.chapterId;
    this.setMode();
    if (this.mode === 'update' && this.topicId) {
      this.getTopicData();
    }
  },
};
</script>

<style>
/* Стили, если требуется */
</style>