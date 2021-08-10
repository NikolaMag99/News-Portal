<template>
  <div class="pt-5">
    <form method="post" @submit.prevent="postNews()" >
      <div class="form-group">
        <label for="naslov">Naslov</label>
        <input style="margin-top: 10px;" v-model="title" type="text" class="form-control" id="naslov" placeholder="Enter title">
      </div>
      <div class="form-group">
        <label for="tekst" style="margin-top: 10px;">Content</label>
        <textarea style="margin-top: 10px;" cols="40" rows="5" v-model="content" type="text" class="form-control" id="tekst" placeholder="Enter content">
     </textarea>
      </div>
      <br>
      <div class = "row"  style="text-align: center">
      <div class="col form-group">
        <b-form-select v-model = "selectKategorija" class="m-3">
          <b-form-select-option  v-for="category in kategorija" :key="category.name" :value= "category" >{{category.name}}</b-form-select-option>
        </b-form-select>
<!--        <b-dropdown text="Kategorije"  variant="primary" class="e-auto mb-2 mb-lg-0" style="height: 35px; margin-top: 5px">-->
<!--          <b-dropdown-item href="#"  v-model = "kategorija"  v-for="category in kategorija" :key="category.name"  @click="find(category.name)">{{category.name}}</b-dropdown-item>-->
<!--        </b-dropdown>-->
       </div>
        <div class="col" >
          <router-link :to="{name: 'AddCategory'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AddCategory'}">Dodaj kategoriju</router-link>
        </div>
      <div class="col form-group">
<!--        <b-dropdown text="Korisnici"   variant="primary" class="e-auto mb-2 mb-lg-0" style="height: 35px; margin-top: 5px">-->
<!--          <b-dropdown-item href="#" v-model = "author" v-for="korisnik in users" :key="korisnik.email"  @click="find(korisnik.email)">{{korisnik.email}}</b-dropdown-item>-->
<!--        </b-dropdown>-->
        <b-form-select v-model = "selectKorisnici" class="m-3">
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
    <br>  <br>
    <table class=" table text-center" style="width: 650px;margin-left: 300px;">

      <thead>
      <tr>
        <!--            <th scope="col">ID</th>-->
        <th scope="col">Title</th>
        <th scope="col">Created At</th>
        <th scope="col">Content</th>
        <th scope="col">Delete</th>
        <th scope="col">Edit</th>
      </tr>
      </thead>

      <tbody >

      <tr v-for="news in vest" :key="news.id" >

        <b-card style="margin-top: 10px">
          <td scope="row" @click="find(news.id)"> {{ news.title }}</td>
        </b-card>

        <td>{{new Date(news.createdAt).toISOString().split('T')[0] }}</td>
        <td>{{ news.content | shortText }}</td>
        <td scope="row">
          <b-button v-if="vest.length > 1" @click="deleteNews(news.id)" size="sm">Delete</b-button>
        </td>
        <td scope="row">
          <b-button @click="editNews(news.id)" size="sm">Edit</b-button>
        </td>

      </tr>

      </tbody>

    </table>

  </div>
</template>

<script>


export default {
  name: "AddNews",
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
      date: '',
      selectedCategory: null,
      title: null,
      content: null,
      kategorija: [],
      selectKategorija: [],
      selectKorisnici: [],
      users: [],
      vest: []
    }
  },
  mounted() {
    this.$axios.get('/api/category').then((response) => {
      console.log("Neka glupost kategorija")
      this.kategorija = response.data;
      console.log(response)
    });
    this.$axios.get('/api/users').then((response) => {
      this.users = response.data;
      console.log(response)
    });
    this.$axios.get('/api/news').then((response) => {
      console.log("Neka glupost")
      this.vest = response.data;
      console.log(response)
    });
  },
  methods: {
    deleteNews(id) {
      this.$axios.delete(`api/news/${id}`).then(() => {
        this.tag.splice(id, 1);

      });
      window.location.reload()
    },
    find(id) {
      this.$router.push(`/news/${id}`);

    },
    editNews(id) {
      this.$router.push(`/news/update/${id}`)
      console.log("UPDATE")
    },
    postNews(){
      var x = 0;
      console.log("DODAJ ME")
      this.$axios.post(`/api/news`, {
        "title": this.title,
        "content": this.content,
        "visits": x,
        "author": this.selectKorisnici,
        "kategorija":this.selectKategorija

      }).then(() => {
        window.location.reload();
      });
    },
  }
}
</script>

<style scoped>

</style>
