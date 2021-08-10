<template>
  <div class="news">
    <h1 class="mt-4">Category</h1>

    <div class="row">
      <div class="col-4">
        <table class="table">
          <thead>
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="category in kategorija" :key="category.name" @click="selectedNews = category">
            <th scope="row">{{ category.name }}</th>
            <td>{{ category.description | shortText }}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="col-6">
        <subject v-if="selectedNews" :category="selectedNews"></subject>
      </div>
    </div>
  </div>
</template>
<script>
import Category from "../components/Category";

export default {

  components: Category,
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
      kategorija: []
    }
  },
  mounted() {
    this.$axios.get('/api/category').then((response) => {
      console.log("Neka glupost kategorija")
      this.kategorija = response.data;
      console.log(response)
    });
  }
}
</script>

