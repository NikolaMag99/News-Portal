<template>
  <div>
    <UserCom :korisnici="users"></UserCom>
  </div>
</template>

<script>
import UserCom from "../components/UserCom";
export default {
  name: "SingleUser",

  components: {UserCom},
  data() {
    return {
      users: {},
    }
  },
  created() {
    this.$axios.get(`/api/users/${this.$route.params.email}`).then((response) => {
      this.users = response.data;
    });
  }
}
</script>

<style scoped>

</style>
