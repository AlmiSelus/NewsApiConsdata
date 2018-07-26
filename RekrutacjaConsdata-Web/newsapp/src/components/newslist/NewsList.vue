<template>
    <div>
        <news v-for="news in newsList" :news="news"></news>
        <pagination :page-info="pageInfo"></pagination>
    </div>
</template>
<script>
    import News from "../news/News";
    import {NewsRestMixin} from "../../mixins/news.rest.mixin";
    import Pagination from "../pagination/Pagination";
    export default {
        name: 'NewsList',
        components: {Pagination, News},
        mixins: [NewsRestMixin],
        data: function() {
            return {
                newsList: [],
                pageInfo: {}
            }
        },
        beforeMount: function () {
            this.getAllNews('pl', 'technology').then(function(data){
                this.newsList = data.body.articles;
                this.pageInfo = {
                    currentPage: data.body.page,
                    totalPages: data.body.totalPages
                };
            });
        }
    }
</script>

<style scoped>

</style>