<html>
    <head>
    <script src="https://lf26-cdn-tos.bytecdntp.com/cdn/expire-1-M/vue/2.6.14/vue.min.js"></script>
    </head>
    <body>


    <div id="app">
        <div id="message">
            <div id="ok" style="color:green">{{message}}</div>
            <div id="error" style="color:red">{{message_error}}</div>
        </div>


        <div>
            <form id="form1" method="POST"> 

                <div>
                    <div>用户名:</div>
                    <input  type="text" v-model="userName">
                </div>
                <div>
                    <div>密码:</div>
                    <input  type="password" v-model="passWord">
                </div>
                <div>
                    <div>确认密码:</div>
                    <input  type="password" v-model="repeatPassWord">
                </div>

                <div>
                    <button type="button" @click="login">登录</button>
                    <button type="reset">重置</button>
                </div>
            </form>
        </div>
    </div>

    </body>



<script>

var app = new Vue({
    el: '#app',
    data: {
        message: '',
        message_error: "",
        userName: "",
        passWord: "",
        repeatPassWord: ""
    },
    methods: {
        login: function(){
            that = this;
            fetch("/api/register",{
                method:"POST",
                body: JSON.stringify({
                    "userName": that.userName,
                    "passWord": that.passWord,
                    "repeatPassWord" : that.repeatPassWord
                })
            }).then(function(response){
                return response.json()
            }).then(function(respone){
                
                if (respone.ok == 1) {
                    that.message = respone.message;
                    that.message_error = "";
                }else{
                    that.message = "";
                    that.message_error = respone.message;
                }
            }).catch(error => function(e){
                console.log("error");
                console.log(e);
            });
        }
    }

})
</script>
</html>