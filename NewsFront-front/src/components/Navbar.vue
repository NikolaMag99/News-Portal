<template >


  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">Web programiranje</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
<!--            <li class="nav-item">-->
<!--              <router-link to="/" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Home'}">Pocetna</router-link>-->
<!--            </li>-->
            <li class="nav-item">
              <router-link :to="{name: 'News'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'News'}">Vesti</router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{name: 'TopNews'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'TopNews'}">Najcitanije</router-link>
            </li>
            <b-dropdown text="Kategorije"   variant="primary" class="e-auto mb-2 mb-lg-0" style="height: 35px; margin-top: 5px">
              <b-dropdown-item href="#"  v-for="category in kategorija" :key="category.name"  @click="find(category.name)">{{category.name}}</b-dropdown-item>
            </b-dropdown>
            <li v-if="canLogout" class="nav-item">
              <router-link :to="{name: 'AddNews'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AddNews'}">Dodaj vest</router-link>
            </li>
            <li  v-if="canLogout" class="nav-item">
              <router-link :to="{name: 'AddCategory'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AddCategory'}">Dodaj kategoriju</router-link>
            </li>
            <li v-if="canLogout" class="nav-item">
              <router-link :to="{name: 'AddTag'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AddTag'}">Dodaj Tag</router-link>
            </li>
            <li v-if="canLogout" class="nav-item">
              <router-link :to="{name: 'AddUser'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AddUser'}">Dodaj korisnika</router-link>
            </li>
<!--            <li class="nav-item">-->
<!--              <router-link :to="{name: 'Category'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Category'}">Category</router-link>-->
<!--            </li>-->
          </ul>
          <form v-if="canLogout" class="d-flex" @submit.prevent="logout">
            <button class="btn btn-outline-secondary" type="submit">Logout</button>
          </form>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: "Navbar",
  computed: {
    canLogout() {
      return this.$route.name !== 'Login';
    }
  },
  data() {
    return {
      selectedCategory: null,
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
    logout() {
      localStorage.removeItem('jwt');
      this.$router.push({name: 'Login'});
    },
    find(name) {
      // this.$axios.get(`/news/kategorija/${name}`).then((response) => {
      //   console.log("Kategorija")
      //   this.kategorija = response.data;
      //   console.log(response)
      // });

      this.$router.push(`/news/kategorija/${name}`).then(() => {
        window.location.reload();
      });
      // this.$router.go()
      console.log(name)

    }
  }
}
</script>

<style scoped>

</style>
