<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "editOldUser()" >
      <div class="form-group">
        <label for="mejl">Email</label>
        <input style="margin-top: 10px;" required  v-model="users.email" v-text="users.email" type="email" class="form-control" id="mejl" >
      </div>
      <br>
      <div class="form-group">
        <label for="ime" style="margin-top: 10px;">Ime</label>
        <input style="margin-top: 10px;" required  v-model="users.first_name" v-text="users.first_name" type="name" class="form-control" id="ime" placeholder="Enter name">

      </div>
      <br>
      <div class="form-group">
        <label for="prezime" style="margin-top: 10px;">Prezime</label>
        <input style="margin-top: 10px;" required  v-model="users.last_name" v-text="users.last_name" type="lastname" class="form-control" id="prezime" placeholder="Enter lastname">
      </div>
      <br>
      <div class="form-group">
        <label for="lozinka" style="margin-top: 10px;">Lozinka</label>
        <input style="margin-top: 10px;"  required  v-model="password"  type="password" class="form-control" id="lozinka" placeholder="Enter password">
      </div>
      <br>
      <div class="form-group">
        <label for="tip" style="margin-top: 10px;">Tip</label>
        <input style="margin-top: 10px;" required  v-model="type"  type="number" class="form-control" id="tip" placeholder="Enter type">
      </div>
<!--      <br>-->
<!--      <div class="form-group">-->
<!--        <label for="status" style="margin-top: 10px;">Status</label>-->
<!--        <input style="margin-top: 10px;" required  v-model="status" type="number" class="form-control" id="status" placeholder="Enter status">-->
<!--      </div>-->
      <br>

      <button type="submit" class="btn btn-primary mt-2">Izmeni korisnika</button>
    </form>

    <h1 class="mt-4">Korisnici</h1>
    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 650px;margin-left: -150px;">
          <thead>
          <tr>
            <th scope="col">Email</th>
            <th scope="col">Ime</th>
            <th scope="col">Prezime</th>
            <th scope="col">Tip</th>
            <th scope="col">Status</th>
          </tr>
          </thead>
          <tbody >

            <b-card style="margin-top: 10px">
              <td scope="row"> {{ korisnici.email }}</td>
            </b-card>
            <td scope="row"> {{ korisnici.first_name }}</td>
            <td scope="row"> {{ korisnici.last_name }}</td>
            <td scope="row"> {{ korisnici.type }}</td>
            <td scope="row"> {{ korisnici.status }}</td>
          </tbody>
        </table>
      </div>

    </div>
  </div>
</template>

<script>


export default {
  name: "UserCom",
  props: {
    korisnici: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      email: null,
      first_name: null,
      last_name: null,
      password: null,
      type: null,
      status: null,
      users: null
    }
  },
  mounted() {
    this.$axios.get(`/api/users/${this.$route.params.email}`).then((response) => {
      this.users = response.data;
    });
  },
  methods: {
    editOldUser(){
      console.log("Kliknuo edit")
      this.$axios.post(`/api/users/${this.$route.params.email}`, {
        "email": this.users.email,
        "first_name": this.users.first_name,
        "last_name": this.users.last_name,
        "password": this.password,
        "type": this.type,
        // "status": this.status,

      }).then(() => {
        this.$router.push(`/users`);
      })
    },

  }
}
</script>

<style scoped>

</style>
