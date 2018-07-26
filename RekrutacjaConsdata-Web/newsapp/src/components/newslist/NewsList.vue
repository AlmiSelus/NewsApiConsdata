<template>
    <div>
        <news v-for="news in list" :news="news"></news>
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
        props: {
            list: Array
        }
    }
</script>

<style scoped>

</style>