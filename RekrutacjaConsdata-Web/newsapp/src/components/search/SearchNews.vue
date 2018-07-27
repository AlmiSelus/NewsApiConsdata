<template>
    <div class="md-toolbar-row">
        <div class="md-toolbar-section-start">
        </div>

        <md-field>
                <md-input v-model="searchData.searchQuery" :disabled="searchInputsDisabled"></md-input>
        </md-field>

        <div class="md-toolbar-section-end">
            <md-field>
                <label for="category">Category</label>
                <md-select v-model="searchData.category" name="category" id="category" :disabled="searchInputsDisabled">
                    <md-option v-for="category in categories" :value="category">{{category}}</md-option>
                </md-select>
            </md-field>

            <md-button class="md-icon-button" v-on:click="validateInput"  :disabled="searchInputsDisabled">
                <md-icon>search</md-icon>
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
                searchData: {}
            }
        },
        props: {
            newsMetadata: {},
            searchInputsDisabled: false
        },
        methods: {
            validateInput: function() {
                let inputValue = this.searchData.searchQuery;
                console.log('Input value = ', inputValue);
                let invalid = inputValue === 'undefined';
                if(!invalid) {
                    // ensure page returns to 1
                    this.newsMetadata.currentPage = 1;
                    this.newsMetadata.query = inputValue;
                    this.newsMetadata.category = this.searchData.category;

                    this.$emit('metadata-changed', this.newsMetadata);
                }
            }
        },
        beforeMount: function () {
            this.getCategories().then(function(data){
                this.categories = data.body;
            });
            this.searchData.category = this.newsMetadata.category;
        }
    }
</script>