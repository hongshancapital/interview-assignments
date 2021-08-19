<?php
declare (strict_types = 1);

namespace app\model;

use think\Model;
use think\facade\Db;
//use think\facade\Log;
/**
 * @mixin \think\Model
 */
class UserModel extends Model
{
    //数据库的名称跟类名不一致的情况必须加。
    protected $name = 'user';

    /**
     * @param $data array 插入添加
     * @return array
     */
    public function regster($data){
        if(!empty($data['username'])){
           $user = $this->getUserByName($data['username']);
           if($user['code']==200 && !empty($user['data'])){
                return ['code'=>400,'msg'=>'已存在该账户无法重复注册'];
           }
           $data['create_time'] = date('Y-m-d H:i:s');
           $res = Db::name('user')->insert($data);
//           Log::record($data['create_time'].'执行了一条注册插入语句,用户名'.$data['username'].'状态：'.$res);
           if($res){
               return ['code'=>200,'msg'=>'注册成功','data'=>[]];
           }
            return ['code'=>400,'msg'=>'注册失败','data'=>[]];
        }
        return ['code'=>400,'msg'=>'缺少参数','data'=>[]];
    }


    /**
     * @param $username 根据用户名查询
     * @return array
     */
    public function getUserByName($username){
        if(!empty($username)){
            $list = Db::name('user')->where('username',$username)->find();
            return ['code'=>200,'msg'=>'查询成功','data'=>$list];
        }
        return ['code'=>400,'msg'=>'缺少参数','data'=>[]];
    }
}
