<template>
  <row :gutter="60">
    <i-col span="12">
      <row>
        <i-col span="8">
          <h1>生成短链接</h1>
        </i-col>
      </row>
      <row :gutter="30">
        <i-col span="8">
          <h4>请输入长连接：</h4>
        </i-col>
        <i-col span="16">
          <Input type="text" v-model="longUrl"/>
        </i-col>
      </row>
      <br>
      <row>
        <i-col span="24">
          <Button type="info" size="large" @click="gen()">生成短链接</Button>
        </i-col>
      </row>
      <br>
      <row>
        <i-col span="24">
          <p>{{newShortUrl}}</p>
        </i-col>
      </row>
    </i-col>
    <i-col span="12">
      <row>
        <i-col span="12">
          <h1>获取原始链接</h1>
        </i-col>
      </row>
      <row :gutter="30">
        <i-col span="8">
          <h4>请输入短连接：</h4>
        </i-col>
        <i-col span="16">
          <Input type="text" v-model="shortUrl"/>
        </i-col>
      </row>
      <br>
      <row>
        <i-col span="24">
          <Button type="info" size="large" @click="getUrl">查询</Button>
        </i-col>
      </row>
      <br>
      <row>
        <i-col span="24">
          <p>
            {{oldLongUrl}}
          </p>
        </i-col>
      </row>
    </i-col>
  </row>

</template>

<script>
  import {shorten, getLongUrl} from "../api/shorturl"

export default {

  name: 'HelloWorld',
  props: {
    msg: String
  },
  data() {
    return {
      longUrl: '',
      shortUrl: '',
      oldLongUrl: '',
      newShortUrl: ''
    };
  },
  methods: {
    gen(){
      let _this = this;
      shorten({url: _this.longUrl}, (res) => {
        if (res.data){
          _this.newShortUrl = res.data;
        }else {
          _this.newShortUrl = '';
        }
      });
    },
    getUrl(){
      let _this = this;
      let arr = _this.shortUrl.split('/');
      let key = arr[arr.length - 1];
      getLongUrl({key: key}, (res) => {
        if (res.data){
          _this.oldLongUrl = res.data;
        }else {
          _this.oldLongUrl = '';
        }
      });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
