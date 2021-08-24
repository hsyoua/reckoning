import Vue from 'vue'
import App from './App.vue'
import router from './router'

import directives from '@/components/Select'
Vue.use(directives)

//element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

import './utils' 

import store from './store'

// mock
process.env.NODE_ENV == 'mock' && require('./mock');


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
