import Vue from 'vue';
import {
    Container,
    Header,
    Aside,
    Main,
    Menu,
    MenuItem,
    MenuItemGroup,
    Submenu,
    Breadcrumb,
    BreadcrumbItem
} from 'element-ui';

// 使用到的组件的样式
import 'element-ui/lib/theme-chalk/container.css';
import 'element-ui/lib/theme-chalk/header.css';
import 'element-ui/lib/theme-chalk/aside.css';
import 'element-ui/lib/theme-chalk/main.css';
import 'element-ui/lib/theme-chalk/menu.css';
import 'element-ui/lib/theme-chalk/menu-item.css';
import 'element-ui/lib/theme-chalk/menu-item-group.css';
import 'element-ui/lib/theme-chalk/submenu.css';
import 'element-ui/lib/theme-chalk/breadcrumb.css';
import 'element-ui/lib/theme-chalk/breadcrumb-item.css';

// 文字图标
import 'element-ui/lib/theme-chalk/icon.css';

// 引入动画
import CollapseTransition from 'element-ui/lib/transitions/collapse-transition.js';
Vue.component(CollapseTransition.name, CollapseTransition);

// 引入组件
Vue.use(Container);
Vue.use(Header);
Vue.use(Aside);
Vue.use(Main);
Vue.use(Menu);
Vue.use(MenuItem);
Vue.use(MenuItemGroup);
Vue.use(Submenu);
Vue.use(Breadcrumb);
Vue.use(BreadcrumbItem);
