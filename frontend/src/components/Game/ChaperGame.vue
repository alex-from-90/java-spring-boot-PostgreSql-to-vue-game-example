<template>
  <div class="container">
    <h3>Все темы</h3>

    <div>
      <ul class="list-group">
        <li class="list-group-item" v-for="chapter in chapters" :key="chapter.id">
          <button class="btn btn-info btn-lg btn-block" @click="allTopics(chapter.id)">
            {{ chapter.chapterName }}
          </button>
        </li>
      </ul>
    </div>

    <div class="row mt-3">
      <div class="col-md-6">
        <button class="btn btn-danger" @click="goBackToGame()">Назад</button>
      </div>
    </div>
  </div>
</template>

<script>
import ChapterService from '@/service/admin/ChapterDataService';

export default {
  name: 'Game',
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
            // Добавьте обработку ошибок, например, отображение сообщения об ошибке
          });
    },
    allTopics(chapterId) {
      this.$router.push({ path: '/gametopics', query: { chapterId: chapterId } });
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
