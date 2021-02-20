import Vue from 'vue'
import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';
ViewUI.Message.config({
    duration: 3
});
Vue.use(ViewUI, {
    transfer: true,
});

import '@/iview-variables.less'
