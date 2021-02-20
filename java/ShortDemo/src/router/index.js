import Vue from 'vue'
import Router from 'vue-router'
import shortView from '@/view/shortView.vue';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'shortView',
      component: shortView
    }
  ]
})
