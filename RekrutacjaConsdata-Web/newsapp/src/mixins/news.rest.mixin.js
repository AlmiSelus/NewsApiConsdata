export const NewsRestMixin ={
    methods: {
        getAllNews: function(language, category) {
            return this.$http.get('http://localhost:9080/news/'+language+'/'+category);
        },

        getCategories: function() {
            return this.$http.get('http://localhost:9080/news/categories');
        }
    }
};