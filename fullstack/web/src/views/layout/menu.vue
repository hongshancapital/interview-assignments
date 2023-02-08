<template>
    <el-collapse-transition>
        <!-- :show-timeout="0"
            :hide-timeout="0" -->
        <el-menu default-active="1" :collapse="isHideMenu" class="layout_menu" background-color="#16181D" text-color="rgba(255,255,255,.7)" active-text-color="#409EFF">
            <el-menu-item index="1" @click="redirect('/index')">
                <i class="el-icon-menu"></i>
                <span slot="title">首页</span>
            </el-menu-item>
        </el-menu>
    </el-collapse-transition>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import { State } from 'vuex-class';

@Component
export default class Menu extends Vue {
    @State('menuHidden')
    private menuHiddenStatus!: boolean;

    private get isHideMenu() {
        return this.menuHiddenStatus;
    }

    redirect(path: string): void {
        if (path !== this.$route.path) {
            this.$router.replace({ path, append: true });
        }
    }
}
</script>

<style lang="less" scoped>
.layout_menu {
    overflow: hidden;
    height: calc(100% - @layout_head_height) !important;
}

.layout_menu:hover {
    overflow-y: auto;
}

.layout_menu::-webkit-scrollbar {
    width: 4px;
}

.layout_menu::-webkit-scrollbar-thumb {
    background: @theme_color;
}

.layout_menu::-webkit-scrollbar-track {
    background: rgba(0, 0, 0, 0);
    box-shadow: inset 0 0 0px rgba(0, 0, 0, 0);
}
</style>
