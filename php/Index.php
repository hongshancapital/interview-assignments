<?php
namespace app\controller;

use app\BaseController;
use app\model\UserModel;
class Index extends BaseController
{
//    public function index()
//    {
//        return '<style type="text/css">*{ padding: 0; margin: 0; } div{ padding: 4px 48px;} a{color:#2E5CD5;cursor: pointer;text-decoration: none} a:hover{text-decoration:underline; } body{ background: #fff; font-family: "Century Gothic","Microsoft yahei"; color: #333;font-size:18px;} h1{ font-size: 100px; font-weight: normal; margin-bottom: 12px; } p{ line-height: 1.6em; font-size: 42px }</style><div style="padding: 24px 48px;"> <h1>:) </h1><p> ThinkPHP V' . \think\facade\App::version() . '<br/><span style="font-size:30px;">14载初心不改 - 你值得信赖的PHP框架</span></p><span style="font-size:25px;">[ V6.0 版本由 <a href="https://www.yisu.com/" target="yisu">亿速云</a> 独家赞助发布 ]</span></div><script type="text/javascript" src="https://tajs.qq.com/stats?sId=64890268" charset="UTF-8"></script><script type="text/javascript" src="https://e.topthink.com/Public/static/client.js"></script><think id="ee9b1aa918103c4fc"></think>';
//    }

    //登录页面
    public function login()
    {
       return view('/index');
    }

    /**注册方法
     * @param username string  账号
     * @param  passwd string  密码 //双md5加密方式
     * @return json
     */
    public function register()
    {
        //1.验证
        $check = $this->check();
        if($check['code']==400){
            return json_encode($check);
        }
        //2.数据库操作
        $data['username'] = $_POST['username'];
        $data['passwd'] = md5(md5($_POST['passwd']));
        $model = new UserModel();
        $res = $model->regster($data);
        if($res['code']== 200){
            return json_encode($res);
        }
        return json_encode($res);
    }

    /** 注册验证方法
     * @param username string  账号
     * @param  passwd string  密码
     * @return array
     */
    public function check(){
        if(empty($_POST['username'])){
            $json_data = [
                'code' => 400,
                'msg'  => '用户名不能为空',
                'data' => []
            ];
            return $json_data;
        }

        if(!preg_match("/^[a-zA-Z_](.*)?$/",$_POST['username']) ){
            $json_data = [
                'code' => 400,
                'msg'  => '用户名只能以英文字母或下划线开头',
                'data' => []
            ];
            return $json_data;
        }

        if(!preg_match("/^[a-zA-Z0-9_]+$/",$_POST["username"])){
            $json_data = [
                'code' => 400,
                'msg'  => '用户名只能包含英文字母，下划线或数字',
                'data' => []
            ];
            return $json_data;
        }

        if(empty($_POST['passwd'])){
            $json_data = [
                'code' => 400,
                'msg'  => '密码不能为空',
                'data' => []
            ];
            return $json_data;
        }

        $reg1 = '/([.*?]){6,}$/'; //密码必须是6位以上
        if(strlen($_POST['passwd'])<6){
            $json_data = [
                'code' => 400,
                'msg'  => '密码必须是6位以上',
                'data' => []
            ];
            return $json_data;
        }

        $reg2 = '/(123|234|345|456|567|678|789|012)/'; //不能有3个连续数字
        if(preg_match($reg2,$_POST['passwd'])){
            $json_data = [
                'code' => 400,
                'msg'  => '密码不能有3个连续数字',
                'data' => []
            ];
            return $json_data;
        }

        $reg3 ='/(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*.])/';//必须含有字母、数字、特殊符号
        if(!preg_match($reg3,$_POST['passwd'])){
            $json_data = [
                'code' => 400,
                'msg'  => '密码必须含有字母、数字、特殊符号',
                'data' => []
            ];
            return $json_data;
        }
        return ['code'=>200];
    }
}
