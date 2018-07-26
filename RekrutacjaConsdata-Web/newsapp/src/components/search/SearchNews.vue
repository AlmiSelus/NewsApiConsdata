<template>
        <div class="md-toolbar-row">
            <div class="md-toolbar-section-start">
            </div>

            <md-field>
                <md-input v-model="searchData.query"></md-input>
            </md-field>

            <div class="md-toolbar-section-end">
                <md-field>
                    <label for="category">Category</label>
                    <md-select v-model="searchData.category" name="category" id="category">
                        <md-option v-for="category in categories" :value="category">{{category}}</md-option>
                    </md-select>
                </md-field>

                <md-button class="md-icon-button">
                    <md-icon>search</md-icon>
                </md-button>

                <md-button class="md-icon-button">
                    <md-icon>refresh</md-icon>
                </md-button>

            </div>
        </div>
</template>

<script>
    import {NewsRestMixin} from "../../mixins/news.rest.mixin";

    export default {
        name: 'NewsSearch',
        mixins: [NewsRestMixin],
        data: function() {
            return {
                categories: [],
                searchData: { category: '', query: '', lang: 'pl' }
            }
        },
        beforeMount: function () {
            this.getCategories().then(function(data){
                this.categories = data.body;
            })
        }
    }
</script>