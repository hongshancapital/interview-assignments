<template>
    <div>
        <Title></Title>
        <Options></Options>
    </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';

@Component({
    components: {
        Title: () => import(/* webpackChunkName: 'layout' */ './headerBreadcrumb.vue'),
        Options: () => import(/* webpackChunkName: 'layout' */ './headerOption.vue')
    }
})
export default class Head extends Vue { }
</script>

<style lang="less" scoped></style>
