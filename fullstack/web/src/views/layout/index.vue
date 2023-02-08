<template>
    <el-container>
        <el-aside :width="isHideMenu ? '64px' : '220px'">
            <Logo></Logo>
            <Menu></Menu>
        </el-aside>
        <el-container>
            <el-header height="50px">
                <Header></Header>
            </el-header>
            <el-main class="root_main">
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import { State } from 'vuex-class';

@Component({
    components: {
        Menu: () => import(/* webpackChunkName: 'layout' */ './menu.vue'),
        Logo: () => import(/* webpackChunkName: 'layout' */ './logo.vue'),
        Header: () => import(/* webpackChunkName: 'layout' */ './header/index.vue')
    }
})
export default class Layout extends Vue {
    @State('menuHidden')
    private menuHiddenStatus!: boolean;

    private get isHideMenu() {
        return this.menuHiddenStatus;
    }
}
</script>

<style lang="less" scoped>
@system_background_color: #fbfbfb;

body > .el-container {
    width: 100%;
    height: 100%;
    background-color: @system_background_color;
    color: #666;
}
.el-header {
    background-color: #fff;
    border-bottom: solid 1px #e6e6e6;
    padding: 0;
}

.el-aside {
    background-color: rgb(22, 24, 29);
    border-right: solid 1px #e6e6e6;
    overflow: hidden;
}

.root_main {
    background-color: @system_background_color;
    padding: 0;
    margin: 0;
    overflow: auto;
    border-radius: 0;
    box-shadow: none;
}
</style>
