<template>
  <div>

    <h2>
      Naslov: {{ news.title }}
    </h2>

    <h6 style="margin-top: 18px">
      Kategorija: {{news.kategorija.name}}
    </h6>

    <div v-if="tagovi">
    <h6 style="margin-top: 12px">Tagovi:</h6>
    <h6 v-for="tags in tagovi" :key = "tags.id" @click="toTag(tags.id)">
      {{tags.mainWord}}
    </h6>
    </div>
    <h5  style="margin-top: 12px">
      Autor: {{ news.author.first_name }}
    </h5>

    <h6  style="margin-top: 12px">
      Datum objave: {{new Date(news.createdAt).toISOString().split('T')[0] }}
    </h6>
    <br>
    <h4>Opis:</h4>
    <p>
      {{ news.content }} <br><br>
    </p>
    <br>
    <h2>Dodaj novi komentar</h2>
    <div class="form-group">
      Datum: {{currentDateTime()}}
    </div>
    <br>
    <form method="post" v-on:submit.prevent = "postComment()" >
      <div class="form-group">
        <label for="ime">Ime</label>
        <input required v-model="author" type="text" class="form-control" id="ime" placeholder="Unesi ime">
      </div>
      <br>

      <div class="form-group">
        <label for="tekst">Komentar</label>
        <input required v-model="content" type="text" class="form-control" id="tekst" placeholder="Komentar">
      </div>
      <br>
      <button type="submit" class="btn btn-primary mt-2">Objavi</button>
    </form>

    <br> <br>
    <h3 style="margin-top: 12px">Komentari:</h3>
    <h6 v-for="komentar in komentari" :key = "komentar.id">

     Autor:
      {{komentar.author}}

      <br>
      {{new Date(komentar.createdAt).toISOString().split('T')[0] }}
      <br>
      <b-card style="margin-top: 10px">
      <h4 style="margin-top: 15px;margin-left: 20px">{{komentar.content}}</h4>
      </b-card>
    </h6>


  </div>
</template>

<script>


export default {
  name: "NewsCom",
  props: {
    news: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      tagovi: [],
      komentari: [],
      author: null,
      content: null,
      cratedAt: null,
      vest: null
    }
  },
  mounted() {
    this.$axios.get(`/api/news/newsTag/${this.$route.params.id}`).then((response) => {
      this.tagovi = response.data;
    })
      this.$axios.get(`/api/news/comments/${this.$route.params.id}`).then((response) => {
        this.komentari = response.data;
    })
    this.$axios.get(`/api/news/tag/${this.$route.params.id}`).then((response) => {
      this.vest = response.data;
    });
  },
  methods: {
    postComment(){
      this.$axios.post(`/api/comments`, {
        "author": this.author,
        "content": this.content,
        "vest": this.news


      }).then(() => {
        window.location.reload();
      });
    },
    currentDateTime() {
      const current = new Date();
      const date = current.getFullYear()+'-'+(current.getMonth()+1)+'-'+current.getDate();
      const time = current.getHours() + ":" + current.getMinutes() + ":" + current.getSeconds();
      const dateTime = date +' '+ time;

      return dateTime;
    },
    toTag(id) {
      this.$router.push(`/news/tag/${id}`);
      console.log(id)
    }
  }
}
</script>

<style scoped>

</style>
