<template>
  <div class="container">
    <h3>Все темы</h3>
    <div v-if="message" class="alert alert-success">{{ message }}</div>
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Название темы</th>
          <th>Редактировать</th>
          <th>Удалить</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="chapter in chapters" :key="chapter.id">
          <td>
            <button class="btn btn-info" @click="allTopics(chapter.id)">
              {{ chapter.chapterName }}
            </button>
          </td>
          <td>
            <button class="btn btn-warning" @click="updateChapter(chapter.id)">
              Редактировать
            </button>
          </td>
          <td>
            <button class="btn btn-danger" @click="deleteChapter(chapter.id)">
              Удалить
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="row">
      <div class="col-md-6">
        <button class="btn btn-success" @click="addChapter()">Добавить</button>
        <button class="btn btn-danger" @click="goBackToGame()">
          Назад
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import ChapterService from '@/service/admin/ChapterDataService';

export default {
  name: 'ChapterList',
  data() {
    return {
      chapters: [],
      message: '',

    };
  },
  methods: {
    getChapters() {
      ChapterService.getAllChapters()
          .then((response) => {
            this.chapters = response.data;
          })
          .catch((error) => {
            console.error(error);
            // Добавить обработку ошибок, например, отображение сообщения об ошибке
          });
    },
    updateChapter(id) {
      this.$router.push({ path: '/add', query: { mode: 'update', id: id } });
    },
    deleteChapter(id) {
      ChapterService.deleteChapter(id)
          .then((response) => {
            this.message = response.data.message;
            this.getChapters();
          })
          .catch((error) => {
            console.error(error);
            // Добавить обработку ошибок, например, отображение сообщения об ошибке
          });
    },
    addChapter() {
      this.$router.push({path: '/add', query: {mode: 'add'}});
    },
    allTopics(chapterId) {
      this.$router.push({ path: '/topics', query: { chapterId: chapterId } });
    },
    goBackToGame() {
      this.$router.push('/');
    },
  },
  mounted() {
    this.getChapters();
  },
};
</script>
