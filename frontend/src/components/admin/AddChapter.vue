<template>
  <div>
    <h3>{{ mode === 'add' ? 'Добавление темы' : 'Редактирование темы' }}</h3>
    <div class="container">
      <form @submit.prevent="validateAndSubmit">

        <fieldset class="form-group">
          <label>Название темы</label>
          <input type="text" class="form-control" v-model="chapterName" />
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
import ChapterService from '@/service/admin/ChapterDataService';

export default {
  name: 'AddChapter',
  data() {
    return {
      chapterName: '',
      errors: [],
      mode: '', // Добавляем переменную для хранения режима работы компонента
      chapterId: null, // Добавляем переменную для хранения идентификатора главы при редактировании
    };
  },
  methods: {
    validateAndSubmit() {
      this.errors = [];
      if (this.chapterName === '') {
        this.errors.push('Введите название главы');
      }
      if (this.errors.length === 0) {
        const chapter = {
          chapterName: this.chapterName,
        };
        if (this.mode === 'add') {
          ChapterService.createChapter(chapter)
              .then(() => {
                this.$router.push('/chapters');
              })
              .catch((error) => {
                console.log(error);
              });
        } else if (this.mode === 'update' && this.chapterId) {
          ChapterService.updateChapter(this.chapterId, chapter)
              .then(() => {
                this.$router.push('/chapters');
              })
              .catch((error) => {
                console.log(error);
              });
        }
      }
    },
    goBack() {
      this.$router.push('/chapters');
    },
    setMode() {
      const params = new URLSearchParams(window.location.search);
      this.mode = params.get('mode');
      this.chapterId = params.get('id'); // Получаем id главы из параметров запроса
    },
    // Метод для получения данных главы по её идентификатору при редактировании
    getChapterData() {
      ChapterService.getChapterById(this.chapterId)
          .then((response) => {
            this.chapterName = response.data.chapterName; // Передаем данные главы в поля формы
          })
          .catch((error) => {
            console.error(error);
          });
    },
  },
  mounted() {
    this.setMode();
    if (this.mode === 'update' && this.chapterId) {
      this.getChapterData(); // Вызываем метод для получения данных главы при редактировании
    }
  },
};
</script>

<style>
/* Стили, если требуется */
</style>
