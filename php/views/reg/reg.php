<!DOCTYPE html>
<html>
    <head>
    <script src="../js/jquery.min.js"></script>
    </head>
<body>

<div class="reg_1">
    <div class="reg_1_1">Username:</div>
    <div class="reg_1_2"><input type="text" id="username" value="a" /></div>
</div>
<div class="reg_1">
    <div class="reg_1_1">Password:</div>
    <div class="reg_1_2"><input type="password" id="p" value="11a11a" /></div>
</div>
<div class="reg_1">
    <div class="reg_1_1">Repeat Password:</div>
    <div class="reg_1_2"><input type="password" id="r_p" value="11a11a" /></div>
</div>
<div class="reg_2">
    <input type="button" value="Submit" id="submit" />
</div>
</body>

<style>
    .reg_1
    {
        width:100%;
        height:40px;
        margin: 0 auto;
        padding-top: 20px;
    }
    .reg_1_1
    {
        width:130px;
        height:40px;
        text-align: right;
        float: left;
    }
    .reg_1_2
    {
        width:300px;
        height:40px;
        float: left;
    }
    .reg_2
    {
        width:430px;
        height:40px;
        padding-top: 20px;
        text-align: center;
    }
</style>

<script>
$("#submit").click(function (){
    let username = $("#username").val();
    let p = $("#p").val();
    let r_p = $("#r_p").val();
    $.post("?r=api/register", { 
            "username": username, 
            "p": p, 
            "r_p": r_p, 
            },
            function(data){
                alert(data.msg);
                // if(data.status == 1)
                // {
                //     alert("ok");
                // }
                // else
                // {
                //     alert(data.msg);
                // }
                // console.log(data); //  2pm
        }, "json");
});
</script>
</html>
