<template>
    <div>
        <div class="md-layout">
            <div class="md-layout-item md-size-40"></div>
            <div class="md-layout-item md-size-5">
                <md-button class="md-icon-button md-raised md-primary">
                    <md-icon>navigate_before</md-icon>
                </md-button>
            </div>
            <div class="md-layout-item md-size-10 centered-middle-text">{{pageInfo.currentPage}} of {{pageInfo.totalPages}}</div>
            <div class="md-layout-item md-size-5">
                <md-button class="md-icon-button md-raised md-primary">
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
            pageInfo: {}
        },
        methods: {
            nextPage: function () {

            },
            prevPage: function () {

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