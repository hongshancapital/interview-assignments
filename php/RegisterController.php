<?php
namespace App\Http\Controllers;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\DB;
use App\Http\Requests\Form;

class RegisterController extends Controller
{
    //约定字符串
    const TOKEN = 'API';
    //加载注册页面
    public function index()
    {
        //获取时间戳
        $timeStamp = time();
        //生成8位随机字符串
        $randomStr = $this->createNonceStr(8);
        //生成token
        $token = $this->arithmetic($timeStamp,$randomStr);
        //返回时间戳，随机字符串，token给注册页面
        return view('register',[
            'timeStamp' => $timeStamp,
            'randomStr' => $randomStr,
            'token' => $token
        ]);
    }
    //表单注册
    public function create(Request $request)
    {
        //echo 111;
        $info = $request->all();
        //校验是否是合法提交的数据
        $timeStamp = $info['timeStamp'];
        $randomStr = $info['randomStr'];
        $token = $info['token'];
        $validToken = $this->arithmetic($timeStamp,$randomStr);
        //如果传入的token值跟后台重新生成的验证token相同，那么提交的是合法数据，否则为非法数据
        if ($token == $validToken) {
            //合法数据，对表单数据进行验证
            // dump($request->validate([
            //     'username' =>['required',
            //                   "regex:/^[a-zA-Z_]\w*$/"],
            //     'password' =>['required',
            //                   "regex:/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,}$/"]
            //     ]));die;
            $messages = [
                'username.required'=>'用户名必须填写',
                'username.regex'=>'字母或下划线开头，只能包含字母数字下划线',
                'password.required' =>'密码必须填写',
                'password.regex' =>'密码必须包含数字，大小写字母，不能全为数字或字母'
            ];

            $validator = Validator::make($info,[
                'username' =>['required',
                              "regex:/^[a-zA-Z_]\w*$/"],
                'password' =>['required',
                              "regex:/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,}$/"],
                'repeatpassword' => 'required|same:password'
            ],$messages);
            // 如果验证规则失败，报错，如果验证规则通过，注册信息。
            if ($validator->fails()) {
                return redirect('api/register')
                             ->withErrors($validator)
                             ->withInput();
            } else {
                //如果验证成功，保存数据。
                $data['username'] = $info['username'];
                $data['password'] = $info['password'];
                DB::table('users')->insert($data);
            }

        } else {
            //如果非法数据，返回提交页面
            return redirect('api/register');
        }
        //echo $timeStamp;

    }

    //随机生成字符串
    private function createNonceStr($length = 8) {
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        $str = "";
        for ($i = 0; $i < $length; $i++) {
            $str .= substr($chars, mt_rand(0, strlen($chars) - 1), 1);
        }
        return "z".$str;
    }

    /**
     * @param $timeStamp 时间戳
     * @param $randomStr 随机字符串
     * @return string 返回签名
     */
    private function arithmetic($timeStamp,$randomStr){
        $arr['timeStamp'] = $timeStamp;
        $arr['randomStr'] = $randomStr;
        $arr['token'] = self::TOKEN;
        //按照首字母大小写顺序排序
        sort($arr,SORT_STRING);
        //拼接成字符串
        $str = implode($arr);
        //进行加密
        $signature = sha1($str);
        $signature = md5($signature);
        //转换成大写
        $signature = strtoupper($signature);
        return $signature;
    }



}

