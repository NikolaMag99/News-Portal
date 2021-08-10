<template>
  <div class="pt-5">
    <h2 class="mt-4">Edit News</h2>
    <br>

        <form method="post" v-on:submit.prevent = "editNews()" >
          <div class="form-group">
            <label for="naslov">Naslov</label>
            <input style="margin-top: 10px;" v-model="vest.title" v-text="vest.title" type="text" class="form-control" id="naslov" placeholder="Enter title">
          </div>
          <div class="form-group">
            <label for="tekst" style="margin-top: 10px;">Content</label>
            <textarea style="margin-top: 10px;" cols="40" rows="5" v-model="vest.content" v-text="vest.content" type="text" class="form-control" id="tekst" placeholder="Enter content">
     </textarea>
          </div>
          <br>
          <div class = "row"  style="text-align: center">
            <div class="col form-group">
              <b-form-select v-model = "selectKategorija"  class="m-3">
                <b-form-select-option  v-for="category in kategorija" :key="category.name" :value= "category" >{{category.name}}</b-form-select-option>
              </b-form-select>
            </div>
            <div class="col" >
              <router-link :to="{name: 'AddCategory'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AddCategory'}">Dodaj kategoriju</router-link>
            </div>
            <div class="col form-group">
              <b-form-select v-model = "selectKorisnici"   class="m-3">
                <b-form-select-option v-for="korisnik in users" :key="korisnik.email" :value= "korisnik" >{{korisnik.first_name}}</b-form-select-option>
              </b-form-select>
            </div>

          </div>
          <br>  <br>
          <br>
          <div style="text-align: center">
            <button  type="submit" class="btn btn-primary">Objavi vest</button>
          </div>
        </form>
      </div>
</template>
<script>

export default {
  name: "EditNews",
  props: {
    news: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      vest: null,
      title: null,
      content: null,
      kategorija: [],
      users: [],
      selectKategorija: [],
      selectKorisnici: [],
    }
  },
  mounted() {
    this.$axios.get(`/api/news/${this.$route.params.id}`).then((response) => {
      this.vest = response.data;
    });
    this.$axios.get('/api/category').then((response) => {
      console.log("Neka glupost kategorija")
      this.kategorija = response.data;
      console.log(response)
    });
    this.$axios.get('/api/users').then((response) => {
      this.users = response.data;
      console.log(response)
    });
  },
  methods: {
    editNews() {
      var x = 0;
      this.$axios.post(`/api/news/update`, {
        "id": this.$route.params.id,
        "title": this.vest.title,
        "content": this.vest.content,
        "visits": x,
        "author": this.selectKorisnici,
        "kategorija":this.selectKategorija
      }).then(() => {
        this.$router.push(`/news`);
      });
    },
  }
}
</script>

<style scoped>

</style>
