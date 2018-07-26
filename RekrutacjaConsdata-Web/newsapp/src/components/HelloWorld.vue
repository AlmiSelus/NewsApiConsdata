<template>
  <div class="hello">
      <news-list></news-list>
  </div>
</template>

<script>
import NewsList from "./newslist/NewsList";
export default {
  name: 'HelloWorld',
    components: {NewsList},
    props: {
    msg: String
  }
}
</script>

<style scoped>
</style>
