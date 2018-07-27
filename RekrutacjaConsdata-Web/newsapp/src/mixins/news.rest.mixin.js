export const NewsRestMixin ={
    methods: {
        /**
         * @param metadata: {
         *      language
         *      category
         *      currentPage
         *      totalPages
         *  }
         * @returns {*|PromiseLike<HttpResponse>}
         */
        getAllNews: function(metadata) {
            return this.$http.get('http://localhost:9080/news/' +
                metadata.language + '/' +
                metadata.category + '/' +
                metadata.currentPage);
        },

        getAllNewsSearched: function(metadata) {
            if(!metadata.query) {
                // fallback to standard version
                return this.getAllNews(metadata);
            }
            return this.$http.get('http://localhost:9080/news/'+
                metadata.language + '/' +
                metadata.category + '/' +
                metadata.currentPage + '?query=' + metadata.query);
        },

        getCategories: function() {
            return this.$http.get('http://localhost:9080/news/categories');
        }
    }
};