<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "postTag()" >
      <div class="form-group">
        <label for="ime">Ime Taga</label>
        <input style="margin-top: 10px;" required  v-model="mainWord" type="text" class="form-control" id="ime" placeholder="Enter name">
      </div>
      <br>

      <button type="submit" class="btn btn-primary mt-2">Add Tag</button>
    </form>

    <h1 class="mt-4">Tagovi</h1>
    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 650px;margin-left: -150px;">
          <thead>
          <tr>
            <th scope="col">Id</th>
            <th scope="col">Ime</th>
            <th scope="col">Delete</th>
          </tr>
          </thead>
          <tbody >
          <tr v-for="tagovi in tag" :key="tagovi.id" >
            <b-card style="margin-top: 10px">
              <td scope="row"> {{ tagovi.id }}</td>
            </b-card>
            <td scope="row"> {{ tagovi.mainWord }}</td>
            <td scope="row">
              <b-button v-if="tag.length > 1" @click="deleteTag(tagovi.id)" size="sm">Delete</b-button>
            </td>

          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "AddTag",
  data() {
    return {
      date: '',
      mainWord: null,
      tag: [],
    }
  },
  mounted() {
    this.$axios.get('/api/tags').then((response) => {
      this.tag = response.data;
      console.log(response)
    });
  },
  methods: {
    deleteTag(id) {
      this.$axios.delete(`api/tags/${id}`).then(() => {
        this.tag.splice(id, 1);

      });
      window.location.reload()
    },
    postTag(){
      console.log("Kliknuo post")
      this.$axios.post('/api/tags', {
        "mainWord": this.mainWord
      }).then(() => {
        window.location.reload()
      })
    },
  }
}
</script>

<style scoped>

</style>
