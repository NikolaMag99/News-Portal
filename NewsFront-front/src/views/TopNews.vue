<template>
  <div class="news" style="text-align: center">
    <h1 class="mt-4">TopNews</h1>

    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 800px;margin-left: -250px;">
          <thead>
          <tr>
            <th scope="col">Title</th>
            <th scope="col">Visits</th>
            <th scope="col">Created At</th>
            <th scope="col">Content</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="news in vest" :key="news.id" @click="find(news.id)">

            <b-card style="margin-top: 10px">
            <td scope="row">{{ news.title }}</td>
            </b-card>
            <td>{{ news.visits }}</td>
            <td>{{ new Date(news.createdAt).toISOString().split('T')[0]  }}</td>
            <b-card style="margin-top: 10px">
            <td>{{ news.content | shortText }}</td>
            </b-card>

          </tr>
          </tbody>
        </table>
      </div>
      <div class="col-6">
        <TopNews v-if="selectedNews" :news="selectedNews"></TopNews>
      </div>
    </div>
  </div>


</template>


<script>
import TopNews from "../components/TopNews";

export default {

  components: {TopNews},
  filters: {
    shortText(value) {
      if (value.length < 30) {
        return value;
      }
      return value.slice(0, 30) + '...'
    }
  },
  data() {
    return {
      selectedNews: null,
      vest: []
    }
  },
  methods: {
    find(id) {
      this.$router.push(`/news/${id}`);

    }
  },
  mounted() {
    this.$axios.get('/api/news/visits').then((response) => {
      console.log("Neka glupost Visit")
      this.vest = response.data;
      console.log(response)
    });
  }
}
</script>
