<template>
    <div>
        <news v-for="news in newsList" :news="news"></news>
    </div>
</template>
<script>
    import News from "../news/News";
    import {NewsRestMixin} from "../../mixins/news.rest.mixin";
    export default {
        name: 'NewsList',
        components: {News},
        mixins: [NewsRestMixin],
        data: function() {
            return {
                newsList: [{title: 'test', description: 'testing'}, {title: 'test', description: 'testing'}]
            }
        },
        beforeMount: function () {
            this.getAllNews('pl', 'technology').then(function(data){
                this.newsList = data.body.articles;
            })
        }
    }
</script>

<style scoped>

</style>