<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "postUser()" >
      <div class="form-group">
        <label for="mejl">Email</label>
        <input style="margin-top: 10px;" required  v-model="email" type="email" class="form-control" id="mejl" placeholder="Enter email">
      </div>
      <br>
      <div class="form-group">
        <label for="ime" style="margin-top: 10px;">Ime</label>
        <input style="margin-top: 10px;" required  v-model="first_name" type="name" class="form-control" id="ime" placeholder="Enter name">

      </div>
      <br>
      <div class="form-group">
        <label for="prezime" style="margin-top: 10px;">Prezime</label>
        <input style="margin-top: 10px;" required  v-model="last_name" type="lastname" class="form-control" id="prezime" placeholder="Enter lastname">
      </div>
      <br>
      <div class="form-group">
        <label for="lozinka" style="margin-top: 10px;">Lozinka</label>
        <input style="margin-top: 10px;" required  v-model="password" type="password" class="form-control" id="lozinka" placeholder="Enter password">
      </div>
      <br>
      <div class="form-group">
        <label for="tip" style="margin-top: 10px;">Tip</label>
        <input style="margin-top: 10px;" required  v-model="type" type="number" class="form-control" id="tip" placeholder="Enter type">
      </div>
      <br>
      <div class="form-group">
        <label for="status" style="margin-top: 10px;">Status</label>
        <input style="margin-top: 10px;" required  v-model="status" type="number" class="form-control" id="status" placeholder="Enter status">
      </div>
      <br>

      <button type="submit" class="btn btn-primary mt-2">Dodaj korisnika</button>
    </form>

    <h1 class="mt-4">Korisnici</h1>
    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 650px;margin-left: -150px;">
          <thead>
          <tr>
            <th scope="col">Email</th>
            <th scope="col">Name</th>
            <th scope="col">Type</th>
            <th scope="col">Status</th>
            <th scope="col">Edit</th>
            <th scope="col">Change Status</th>
          </tr>
          </thead>
          <tbody >
          <tr v-for="korisnici in users" :key="korisnici.email" >
            <b-card style="margin-top: 10px">
              <td scope="row"> {{ korisnici.email }}</td>
            </b-card>
            <td scope="row"> {{ korisnici.first_name }}</td>
            <td scope="row"> {{ korisnici.type }}</td>
            <td scope="row"> {{ korisnici.status }}</td>
            <td scope="row">
              <b-button @click="editUser(korisnici.email)" size="sm">Edit</b-button>
            </td>
            <td scope="row" v-if="korisnici.type < 1">
              <b-button  @click="changeStatus(korisnici.email)" size="sm">Change</b-button>
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
  name: "AddUser",
  data() {
    return {
      date: '',
      email: null,
      first_name: null,
      last_name: null,
      password: null,
      status: null,
      type: null,
      users: [],
    }
  },
  mounted() {
    this.$axios.get('/api/users').then((response) => {
      this.users = response.data;
    });
  },
  methods: {
    editUser(email) {
      this.$router.push(`/users/${email}`);
      console.log(email)
    },
    postUser(){
      console.log("Kliknuo post")
      this.$axios.post('/api/users', {
        "email": this.email,
        "first_name": this.first_name,
        "last_name": this.last_name,
        "password": this.password,
        "status": this.status,
        "type": this.type,

      }).then(() => {
        window.location.reload()
      })
    },
    changeStatus(email){
      console.log("Kliknuo post")
      this.$axios.get(`/api/users/status/${email}`).then(() => {
        window.location.reload()
      })
    },
  }
}
</script>

<style scoped>

</style>
