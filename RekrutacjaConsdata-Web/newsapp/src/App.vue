<template>
  <div id="app">
    <md-toolbar id='toolbar'>
      <news-search></news-search>
    </md-toolbar>
    <div class="md-app-content">
      <HelloWorld msg="Welcome to Your Vue.js App"/>
    </div>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import NewsSearch from "./components/search/SearchNews";

export default {
  name: 'app',
  components: {
      NewsSearch,
    HelloWorld
  }
}
</script>

<style>
  #toolbar{
    position:sticky;
    top: 0;
  }
#app {
  font-family: 'Roboto', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
