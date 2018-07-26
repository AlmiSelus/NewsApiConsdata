import Vue from 'vue'
import App from './App.vue'
import VueResource from 'vue-resource'
import DatePicker from 'vue2-datepicker'
import VueLodash from 'vue-lodash'

// Bootstrap
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// Material Design
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.css'
import 'vue-material/dist/theme/default.css'

Vue.use(BootstrapVue);
Vue.use(VueResource);
Vue.use(DatePicker);
Vue.use(VueLodash);
Vue.use(VueMaterial);

Vue.config.productionTip = false;

new Vue({
  render: h => h(App)
}).$mount('#app');
