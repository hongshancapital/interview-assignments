<template>
  <div id="page-box">
    <h3 ref="title" class="banner-title">短链接生成平台</h3>
    <input-box />
    <result-box />
    <div class="api-doc">
    </div>
  </div>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import InputBox from './components/input-box.vue';
import ResultBox from './components/result-box.vue';

@Component({
  components: {
    InputBox,
    ResultBox
  }
})
export default class App extends Vue {
};
</script>
<style>
html,
body {
  width: 100%;
  height: 100%;
}
* {
  margin: 0;
  padding: 0;
}
</style>
<style lang="less" scoped>
#page-box {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.banner-title {
  width: 100%;
  height: 80px;
  line-height: 80px;
  text-align: center;
  font-size: 48px;
  position: relative;
  top: 10%;
  letter-spacing: 5px;
  color: #409EFF;
}
.api-doc{
  text-align: left;
  font-size: 12px;
}
</style>
