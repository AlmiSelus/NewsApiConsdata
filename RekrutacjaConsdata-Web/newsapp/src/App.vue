<template>
    <div id="app">
        <md-toolbar id='toolbar'>
            <news-search :news-metadata="newsMetadata" v-on:metadata-changed="callSearchNews"></news-search>
        </md-toolbar>
        <div class="md-app-content">
            <news-list :list="newsList"></news-list>

            <pagination :news-metadata="newsMetadata"
                      v-on:page-changed="callGetNews"></pagination>
        </div>
    </div>
</template>

<script>
import NewsSearch from "./components/search/SearchNews";
import NewsList from "./components/newslist/NewsList";
import Pagination from "./components/pagination/Pagination";
import {NewsRestMixin} from "./mixins/news.rest.mixin";

export default {
    name: 'app',
    mixins: [NewsRestMixin],
    components: {
        NewsList,
        NewsSearch,
        Pagination
    },
    data: function() {
        return {
            newsList: [],
            newsMetadata: {language: 'pl', category: 'technology', currentPage: 1, totalPages: 0}
        }
    },
    methods: {
        callGetNews: function (metadata) {
            this.getAllNews(metadata).then(function(data){
                this.onSuccess(data, metadata);
            });
        },

        callSearchNews: function (metadata) {
          this.getAllNewsSearched(metadata).then(function(data) {
              this.onSuccess(data, metadata)
          });
        },

        onSuccess: function(data, metadata) {
            this.newsList = data.body.articles;
            this.newsMetadata = {
                language: metadata.language,
                category: metadata.category,
                currentPage: data.body.page,
                totalPages: data.body.totalPages
            };
        }
    },
    beforeMount: function () {
        this.callGetNews(this.newsMetadata);
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
