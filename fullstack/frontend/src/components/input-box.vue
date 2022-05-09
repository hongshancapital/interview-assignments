<template>
  <div class="mdl-input">
    <div class="row row-input">
      <el-input
        v-model="longUrl"
        class="long-url"
        placeholder="请输入您的链接，如：https://xxx.com/xxx"
      />
      <el-button class="button" type="primary" @click="getLink">立即缩短</el-button>
    </div>
    <div class="row">
      <span>有效期：</span>
      <el-select v-model="expires" size="small" class="term_select" placeholder="请选择">
        <el-option :value="1" label="1分钟(测试)" />
        <el-option :value="2" label="7天" />
        <el-option :value="3" label="3个月" />
        <el-option :value="4" label="半年" />
        <el-option :value="0" label="永久" selected />
      </el-select>
    </div>
  </div>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import validUrl from 'valid-url';
import { bus } from '../util';

interface Configs {
  message: string;
  status: boolean;
  link: string;
  expires: number;
}

@Component({ components: {} })
export default class InputBox extends Vue {
  // $bus: any;
  public longUrl: string = '';
  public expires: number = 0;
  public config: Configs = {
    message: '',
    status: false,
    link: '',
    expires: 0
  };
  getLink(): void {
    this.config.message = '';
    this.config.status = true;
    this.config.link = this.longUrl;
    this.config.expires = this.expires;
    if (!this.longUrl.replace(/\s*/g, '') && this.config.status) {
      this.config.message = '长链接不能为空';
      this.config.status = false;
    }
    if (!validUrl.isUri(this.longUrl) && this.config.status) {
      this.config.message = '长链接格式错误';
      this.config.status = false;
    }
    bus.$emit('sendLongUrl', this.config);
  }
};
</script>
<style lang="less" scoped>
a {
  color: #42b983;
}
.mdl-input {
  width: 100%;
  display: flex;
  flex-direction: column;
}
.row {
  width: 40%;
  min-width: 400px;
  display: flex;
  margin: 10px auto;
  line-height: 32px;
  &.row-input {
    margin-top: 50px;
    justify-content: center;
  }
  .long-url {
    width: 100%;
  }
  .button {
    width: 100px;
    margin-left: 10px;
  }
}
</style>
