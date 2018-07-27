<template>
    <div id="app">
        <md-toolbar id='toolbar'>
            <news-search :news-metadata="newsMetadata"
                         :searchInputsDisabled="searchInputsDisabled"
                         v-on:metadata-changed="callSearchNews"></news-search>
        </md-toolbar>
        <div class="md-app-content">
            <md-progress-spinner md-mode="indeterminate"
                                 v-show="isLoading"></md-progress-spinner>

            <news-list :list="newsList" v-show="!isLoading && !isError"></news-list>

            <pagination :news-metadata="newsMetadata"
                      v-on:page-changed="callGetNews"
                      v-show="!isLoading && !isError"></pagination>
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
            isLoading: true,
            isError: false,
            searchInputsDisabled: false,
            newsList: [],
            newsMetadata: {language: 'pl', category: 'technology', currentPage: 1, totalPages: 0}
        }
    },
    methods: {
        callGetNews: function (metadata) {
            this.resetState();
            this.getAllNews(metadata).then(function(data){
                this.onSuccess(data, metadata);
            }, function(error){

            });
        },

        callSearchNews: function (metadata) {
            console.log('Metadata = ', metadata);
            this.resetState();
            this.getAllNewsSearched(metadata).then(function(data) {
                this.onSuccess(data, metadata);
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
            this.isLoading = false;
            this.searchInputsDisabled = false;
        },

        resetState: function() {
            this.isLoading = true;
            this.newsList = [];
            this.searchInputsDisabled = true;
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
