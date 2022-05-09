<template>
  <div class="mdl-result">
    <section v-show="status === 'error'">{{message}}</section>
    <section v-show="status === 'loading'">加载中...</section>
    <section v-show="status === 'successed'" class="success">
      <span>短链接 <a :href="shortUrl" target="_blank">{{shortUrl}}</a></span>
      <span>二维码：<i id="qrcode" ref="qrcode" /></span>
    </section>
  </div>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
// const QRCode: any = require('qrcodejs');
import QRCode from 'QRCode';
import { bus } from '../util';

const config = (window as any).config;

interface Configs {
  message: string;
  status: boolean;
  link: string;
  expires: number;
}

@Component({ components: {} })
export default class ResultBox extends Vue {
  // data
  public message: string = '';
  public status: string = '';
  public longUrl: string = '';
  public shortUrl: string = '';
  public expires: number = 0;

  mounted(): void {
    this.init();
  }

  init(): void {
    this.message = '';
    bus.$on('sendLongUrl', (config: Configs): any => {
      if (config.status) {
        this.longUrl = config.link;
        this.expires = config.expires;
        this.requestShortUrl();
      } else {
        this.status = 'error';
        this.message = config.message;
      }
    });
  }

  requestShortUrl(): void {
    this.status = 'loading';
    const qrcode: any = this.$refs.qrcode;
    qrcode.innerHTML = '';
    fetch(config.origin + '/api/url/shorten', {
      method: 'post',
      headers: {
        'Content-type': 'application/json',
        Accept: 'application/json'
      },
      body: JSON.stringify({
        longUrl: this.longUrl,
        expires: this.expires
      })
    })
      .then((res: any): void => res.json())
      .then((res: any): void => {
        this.shortUrl = res.shortUrl;
        this.status = 'successed';
        this.qrcode();
      });
  }

  qrcode(): void {
    new QRCode(this.$refs.qrcode, this.shortUrl);
  }
}
</script>
<style lang="less" scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
canvas {
  width: 100px;
  height: 100px;
}
canvas img {
  width: 100px;
  height: 100px;
  display: none;
}
.mdl-result {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  // margin-top: 50px;
  flex-direction: column;
}
.success {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
</style>
