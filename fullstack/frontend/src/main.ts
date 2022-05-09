import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';

Vue.use(ElementUI);
// ElementUI.install(Vue, {
//   locale: 'zh-CN',
//   i18n: '',
//   size: 'small'
// });

Vue.config.productionTip = false;
// Vue.prototype.$bus = new Vue();

new Vue({
  render: (h: Function) => h(App)
}).$mount('#app');
