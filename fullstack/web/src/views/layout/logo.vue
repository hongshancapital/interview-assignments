<template>
    <div class="layout_menu_header"></div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';

@Component
export default class Logo extends Vue { }
</script>

<style lang="less" scoped>
.layout_menu_header {
    height: calc(@layout_head_height - 1px);
    color: #fff;
    border-bottom: solid 1px @menu_background_color;
    background-color: rgba(255, 255, 255, 0.1);
}
</style>
