import Vue from 'vue';
import { Route } from 'vue-router';
import App from './App.vue';
import router from './router';
import store from './store';
import i18n from './lang';
import Tips from './ui-frame/ui-tips';
import './ui-frame';
import './assets';

Vue.config.productionTip = false;
Vue.config.performance = true;
Vue.config.errorHandler = async (error: Error /*, vm, info*/) => {
    Tips.error(`${error}`);
};
Vue.config.warnHandler = (msg: string /*, vm, trace*/) => {
    console.error(msg); /* eslint-disable-line no-console */
};

new Vue({
    router,
    store,
    i18n,
    render: h => h(App),
    watch: {
        $route(to: Route /*, from*/) {
            if (to.meta?.title) {
                document.title = `elementUI — ${to.meta.title}`;
            }
        },
        lang() {
            if (this.$i18n.locale !== this.lang) {
                this.$i18n.locale = this.lang;
            }
        }
    },
    created() {
        store.dispatch('setScreenType');
    },
    mounted() {
        let waitForResizeEndTimer: null | number = null;

        window.onresize = () => {
            const waitTime = 500;

            if (waitForResizeEndTimer === null) {
                waitForResizeEndTimer = window.setTimeout(() => {
                    store.dispatch('setScreenType');
                }, waitTime);
            } else {
                clearTimeout(waitForResizeEndTimer);
                waitForResizeEndTimer = window.setTimeout(() => {
                    store.dispatch('setScreenType');
                }, waitTime);
            }
        };
    },
    computed: {
        lang() {
            return store.state.language;
        }
    }
}).$mount('#app');
