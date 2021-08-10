<template>
  <div class="pt-5">
    <h2 class="mt-4">Edit Category</h2>
    <br>

        <form method="post" v-on:submit.prevent = "editCategory()" >
          <div class="form-group">
            <label for="ime">Ime</label>
            <input style="margin-top: 10px;" required  v-model="kategorija.name" v-text="kategorija.email" type="text" class="form-control" id="ime" placeholder="Enter name">
          </div>
          <div class="form-group">
            <label for="opis" style="margin-top: 10px;">Opis</label>
            <input style="margin-top: 10px;" required  v-model="kategorija.description" v-text="kategorija.description" type="text" class="form-control" id="opis" placeholder="Enter desc">

          </div>
          <br>

          <button type="submit" class="btn btn-primary mt-2">Izmeni</button>
        </form>
      </div>
</template>
<script>

export default {
  name: "EditCategory",
  props: {
    category: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      kategorija: null,
      name: null,
      description: null,
    }
  },
  created() {
    console.log("Kate edit")
    this.$axios.get(`/api/category/${this.$route.params.name}`).then((response) => {
      this.kategorija = response.data;
    });
  },
  methods: {
    editCategory() {
      this.$axios.post(`/api/category/${this.$route.params.name}`, {
        "name": this.kategorija.name,
        "description": this.kategorija.description
      }).then(() => {
        this.$router.push(`/category`);
      });
    },
  }
}
</script>

<style scoped>

</style>
