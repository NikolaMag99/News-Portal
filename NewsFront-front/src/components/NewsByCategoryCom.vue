<template>
  <div>

    <h2>
      Naslov: {{ news.title }}
    </h2>

    <h6 style="margin-top: 18px">
      Kategorija: {{news.kategorija}}
    </h6>

    <h6 style="margin-top: 12px">Tagovi:</h6>
    <h6 v-for="tag in tagovi" :key = "tag.id">
      {{tag.mainWord}}
    </h6>

    <h5  style="margin-top: 12px">
      Autor: {{ news.author }}
    </h5>

    <h6  style="margin-top: 12px">
      Datum objave: {{new Date(news.createdAt).toISOString().split('T')[0] }}
    </h6>
    <br>
    <h4>Opis:</h4>
    <p>
      {{ news.content }} <br><br>
    </p>
  </div>
</template>

<script>

export default {
  name: "NewsByCategoryCom",
  props: {
    news: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      tagovi: []
    }
  },
  mounted() {
    this.$axios.get(`/api/news/newsTag/${this.$route.params.id}`).then((response) => {
      this.tagovi = response.data;
    })
  }
}
</script>

<style scoped>

</style>
