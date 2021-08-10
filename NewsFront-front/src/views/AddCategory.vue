<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "postCategory()" >
      <div class="form-group">
        <label for="ime">Naslov</label>
        <input style="margin-top: 10px;" required  v-model="name" type="text" class="form-control" id="ime" placeholder="Enter name">
      </div>
      <div class="form-group">
        <label for="opis" style="margin-top: 10px;">Content</label>
        <input style="margin-top: 10px;" required  v-model="description" type="text" class="form-control" id="opis" placeholder="Enter desc">

      </div>
      <br>

      <button type="submit" class="btn btn-primary mt-2">Add Category</button>
    </form>

    <h1 class="mt-4">Kategorije</h1>
    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 650px;margin-left: -150px;">
          <thead>
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Desc</th>
            <th scope="col">Delete</th>
            <th scope="col">Edit</th>
          </tr>
          </thead>
          <tbody >
          <tr v-for="category in kategorija" :key="category.name">
            <b-card style="margin-top: 10px">
              <td scope="row" @click="findCategory(category.name)"> {{ category.name }}</td>
            </b-card>
            <td scope="row"> {{ category.description }}</td>
            <td scope="row">
              <b-button v-if="kategorija.length > 1" @click="deleteCategory(category.name)" size="sm">Delete</b-button>
            </td>
            <td scope="row">
              <b-button @click="editCategory(category.name)">Edit</b-button>
            </td>
          </tr>



          </tbody>
        </table>
      </div>
<!--      <div class="col-6" >-->
<!--        <NewsCom v-if="selectedNews" :cat="selectedNews"></NewsCom>-->
<!--      </div>-->
    </div>
  </div>
</template>

<script>

export default {
  name: "AddCategory",
  data() {
    return {
      date: '',
      name: null,
      description: null,
      kategorija: [],
    }
  },
  mounted() {
    this.$axios.get('/api/category').then((response) => {
      console.log("Neka glupost kategorija")
      this.kategorija = response.data;
      console.log(response)
    });
  },
  methods: {
    editCategory(name) {
      this.$router.push(`/category/${name}`)
    },
    deleteCategory(name) {
      this.$axios.delete(`api/category/${name}`).then(() => {
        this.kategorija.splice(name, 1);

      });
      window.location.reload()
    },
    postCategory(){
      console.log("Kliknuo post")
      this.$axios.post('/api/category', {
        "name": this.name,
        "description": this.description
      }).then(() => {
        window.location.reload()
      })
    },
    findCategory(name) {
      this.$router.push(`/news/kategorija/${name}`);
    }
  }
}
</script>

<style scoped>

</style>
