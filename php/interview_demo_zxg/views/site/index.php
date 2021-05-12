<?php

/* @var $this yii\web\View */

use yii\helpers\Url;
use yii\web\View;

$this->title = 'My Yii Application';
?>

    <div class="site-index">
        <fieldset>
            <legend>
                <h2>用户注册 Demo</h2>
            </legend>
            <form id="registerForm" role="form" action="<?= Url::to('/api/register') ?>">
                <div class="form-group ">
                    <label for="name">用户名</label>
                    <input type="text" class="form-control" id="userName" name="username" placeholder="请输入用户名">
                </div>
                <div class="form-group">
                    <label for="name">密码</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                </div>
                <div class="form-group">
                    <label for="name">确认密码</label>
                    <input type="password" class="form-control" id="repeatPassword" name="repeat_password" placeholder="请输入密码">
                </div>
                <button type="submit" class="btn btn-default">提交</button>
            </form>
        </fieldset>
    </div>

<?php
$js = <<<JS
$(document).ready(function() {
    
    var LxStr = function(str){
        var arr = str.split('');
        var flag = true;
        for (var i = 1; i < arr.length-1; i++) {
            var firstIndex = arr[i-1].charCodeAt();
            var secondIndex = arr[i].charCodeAt();
            var thirdIndex = arr[i+1].charCodeAt();
            thirdIndex - secondIndex == 1;
            secondIndex - firstIndex==1;
            if((thirdIndex - secondIndex == 1)&&(secondIndex - firstIndex==1)){
                flag =  false;
            }
            if (firstIndex === secondIndex && firstIndex === thirdIndex && secondIndex === thirdIndex) { 
                flag = false;
            }
        }
        return flag;
    }
    
    $("#registerForm button").on("click", function (e) {
        e.preventDefault();
        let _form = $("#registerForm");
        let username       = _form.find("input[name='username']").val();
        let password       = _form.find("input[name='password']").val();
        let repeatPassword = _form.find("input[name='repeat_password']").val();
        let reg = /^[A-Za-z_].*$/
         if (!reg.test(username)) {
             alert("用户名只能以英文字母或下划线开头");
             return false;
         }
        
         reg = /\w\d*$/;
         if (!reg.test(username)) {
             alert("用户名只能包含英文字母，下划线或数字");
             return false;
         }
        
         if(password.length < 6) {
            alert("密码长度必须 > 6");
            return false;  
         }
                
         reg = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[\(\)])+$)([^(0-9a-zA-Z)]|[\(\)]|[a-z]|[A-Z]|[0-9]){6,}$/;
         if (!reg.test(password)) {
             alert("密码必须有大写字母，小写字母或数字中的两项");
             return false;
         }
        
         if (!LxStr(password)) {
             alert("密码不能含有 3 位以上的连续数字");
             return false;
         }
        
         if(password !== repeatPassword) {
            alert("请确认密码");
            return false;  
         }
        $.ajax({
            type:"POST",
            url: "/api/register",
            data:{"username":username,"password":password, "repeat_password":repeatPassword},
            dataType: "JSON",
            success:function(res){
                console.log(res)
                alert(res.msg)
                if (res.code === "1000"){
                    location.reload();
                }
             } 
        })
        
    })
});
JS;
$this->registerJs($js, View::POS_END);
\app\assets\AppAsset::register($this);