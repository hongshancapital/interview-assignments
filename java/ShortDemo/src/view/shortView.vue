<template>

<div style="background: url(bg.jpg);">

    <Tabs value="name1" style="width:50%;height:60%;margin-top:100px;align:center;margin-left:100px;">
        <TabPane label="网址缩短" name="name1">
         <Label font-family="Helvetica Neue">
         请输入长网址：</Label><br><br>
             <Input v-model="form.url" placeholder="Enter url..." style="width: 400px" />
             <Button type="primary" @click="transferShortUrl">网址压缩</Button>
             <p>
             短网址服务可以帮您把长长的网址压缩，让您在140字的微博中包含更多有趣内容！
           </p><br><br>
             可以为您的短网址选择下列后缀：<br><br>
            <RadioGroup v-model="form.suffix">
                  <Radio label="dwz.date">
                      <span>dwz.date</span>
                  </Radio>
                  <Radio label="dwz.win">
                      <span>dwz.win</span>
                  </Radio>
                  <Radio label="cn.hk.uy">
                      <span>cn.hk.uy</span>
                  </Radio>
              </RadioGroup>
          <Modal
            v-model="modal1"
            title="压缩地址"
            @on-ok="ok"
            @on-cancel="cancel">
            <span>压缩地址为：{{dUrl}}</span>
          </Modal>
        </TabPane>
        <TabPane label="网址还原" name="name2">
          <Label font-family="Helvetica Neue">
                 请输入要还原的短网址：</Label><br><br>
                     <Input v-model="form.dUrl" placeholder="Enter url..." style="width: 400px" />
                     <Button type="primary" @click="getOriginalUrl">网址还原</Button>
                     <P>
                     短网址还原可以帮您把短网址还原为真实网址，让您清楚的查看目标网址，避免进入钓鱼网站上当受骗！
                     </P><br><P>
                     短网址还原支持还原哪些短网址呢？
                     本站可以还原所有的短网址链接，包括新浪短网址t.cn、腾讯短网址url.cn、百度短网址url.cn、淘宝短网址taourl.com、谷歌短网址goo.gl等等。如果发现无法还原的情况，请立刻向本站反馈。
                     </P>
                <Modal v-model="modal2"
                  title="源地址"
                  @on-ok="ok"
                  @on-cancel="cancel">
                  <span>原地址为：{{yUrl}}</span>
                </Modal>
        </TabPane>
    </Tabs>
 </div>
</template>
<script>
  import axios from 'axios'
  export default {
        data() {
        return {
          modal1:false,
          modal2:false,
          dUrl:"",
          yUrl:"",
             form: {
               url:"",
               dUrl:"",
               suffix:""
             }
           }
        },
         methods:{
           ok () {
             this.$Message.info('Clicked ok');
           },
           cancel () {
             this.$Message.info('Clicked cancel');
           },
           isURL(str_url) {
             var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
               + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
               + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
               + "|" // 允许IP和DOMAIN（域名）
               + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
               + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
               + "[a-z]{2,6})" // first level domain- .com or .museum
               + "(:[0-9]{1,4})?" // 端口- :80
               + "((/?)|" // a slash isn't required if there is no file name
               + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
             var re = new RegExp(strRegex);
             if (re.test(str_url)) {
               return (true);
             } else {
               return (false);
             }
           },
           transferShortUrl(){
             if(!this.isURL(this.form.url)){
               this.$Message.info('网址不合法');
               return ;
             }
             var url="/short/transferShortUrl";
             console.log("kkfrf"+this.form);
             return axios({
               method: 'get',
               baseURL: '/api',
               url,
               params: {
                 url:this.form.url,
                 suffix:this.form.suffix
               },
               timeout: 10000,
               headers: {
                 'X-Requested-With': 'XMLHttpRequest',
                 'Content-Type': 'application/json'
               }
             }).then(
               (res) => {
                 if(res.data.code==200){
                   this.modal1 = true;
                   this.dUrl = res.data.data;
                 }else{
                   this.$Message.info(res.data.message);
                 }
               }
             )
           },
           getOriginalUrl(){
             var url="/short/getOriginalUrl";
             return axios({
               method: 'get',
               baseURL: '/api',
               url,
               params: {
                 dUrl:this.form.dUrl
               },
               timeout: 10000,
               headers: {
                 'X-Requested-With': 'XMLHttpRequest',
                 'Content-Type': 'application/json'
               }
             }).then(
               (res) => {
                 if(res.data.code==200){
                   this.modal2 = true;
                   this.yUrl = res.data.data;
                 }
               }
             )
           },
         }
    }
</script>
