<template>
  <div>
    <NewsCom :news="vest"></NewsCom>
  </div>
</template>

<script>
import NewsCom from "../components/NewsCom";
export default {
  name: "SingleNews",

  components: {NewsCom},
  data() {
    return {
      vest: {},
    }
  },
  created() {
    this.$axios.get(`/api/news/${this.$route.params.id}`).then((response) => {
      this.vest = response.data;
    });
  }
}
</script>

<style scoped>

</style>
