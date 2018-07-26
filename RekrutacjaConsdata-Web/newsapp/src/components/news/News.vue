<template>
    <md-card class="col-md-3">
        <md-card-media>
            <img :src="news.imageUrl" :alt="news.title">
        </md-card-media>

        <md-card-header>
            <div class="md-title">{{news.title}}</div>
            <div class="md-subhead">{{news.description}}</div>
        </md-card-header>

        <md-card-content>
            <md-button class="md-icon-button md-raised md-primary" :href="news.articleUrl">
                <md-icon>send</md-icon>
            </md-button>
        </md-card-content>
    </md-card>
</template>

<script>
    export default {
        name: 'News',
        props: {
            news: {}
        }
    }
</script>

<style>
    .md-card {
        margin: 10px;
        display: inline-block;
    }
</style>