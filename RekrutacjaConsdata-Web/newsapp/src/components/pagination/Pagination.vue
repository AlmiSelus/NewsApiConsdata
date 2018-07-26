<template>
    <div>
        <div class="md-layout">
            <div class="md-layout-item md-size-40"></div>
            <div class="md-layout-item md-size-5">
                <md-button class="md-icon-button md-raised md-primary"
                           :disabled="newsMetadata.currentPage === 1"
                           v-on:click="prevPage">
                    <md-icon>navigate_before</md-icon>
                </md-button>
            </div>
            <div class="md-layout-item md-size-10 centered-middle-text">{{newsMetadata.currentPage}} of {{newsMetadata.totalPages}}</div>
            <div class="md-layout-item md-size-5">
                <md-button class="md-icon-button md-raised md-primary"
                           :disabled="newsMetadata.currentPage === newsMetadata.totalPages"
                           v-on:click="nextPage">
                    <md-icon>navigate_next</md-icon>
                </md-button>
            </div>
            <div class="md-layout-item md-size-40"></div>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'Pagination',
        props: {
            newsMetadata: {}
        },
        methods: {
            nextPage: function () {
                this.newsMetadata.currentPage+=1;
                this.$emit('page-changed', this.newsMetadata);
            },
            prevPage: function () {
                this.newsMetadata.currentPage-=1;
                this.$emit('page-changed', this.newsMetadata);
            }
        }
    }
</script>

<style scoped>
    .centered-middle-text {
        vertical-align: middle;
        line-height: 40px;
        height: 40px;
    }
</style>