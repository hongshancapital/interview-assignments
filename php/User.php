<?php
namespace app\modules\api\modules\v1\models;

use yii;
use yii\data\Pagination;
use yii\helpers\ArrayHelper;
class User extends \yii\db\ActiveRecord
{

    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'user';
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'id',
            'Username' => 'Username',
            'Password' => 'Password',
            'createTime' => 'createTime',
            'updateTime' => 'updateTime',
        ];
    }
    public function UserInfo($params=array()){
        $result = $this->CheckParams($params);
        if($result['code'] == 1){
            return $result;
        }
        $user = new User;
        $user->Username = $params['Username'];
        $user->Password = $params['Password'];
        if($user->save()){
            return [
                'code' => 0,
                'mesg' => 'success',
                'id' => $user->id,
            ];
        }else{
            return [
                'code' => 1,
                'mesg' => '注册失败',
            ];
        }
    }
    // 验证参数
    public function CheckParams($params=array()){
        $Username = $params['Username'];
        $Password = $params['Password'];

        // 验证username   1、只能以英文字母或下划线开头 2、只能包含英文字母，下划线或数字
        if (!preg_match('/^[a-zA-Z_]$/', substr($Username, 0, 1))) {
            return [
                'code' => 1,
                'mesg' => '用户名只能以英文字母或下划线开头',
            ];
        }
        if (!preg_match('/^[a-zA-Z_][a-zA-Z0-9_]*$/', $Username)) {
            return [
                'code' => 1,
                'mesg' => '用户名只能包含英文字母，下划线或数字',
            ];
        }

        //验证Password   1、长度在6位以上 2、不能含有3位以上的连续数字 3、必须有大写字母，小写字母或数字中的两项
        if (strlen($Password) <= 6) {
            return [
                'code' => 1,
                'mesg' => '密码长度在6位以上',
            ];
        }
        if (preg_match("/\d{3}/", $Password, $matches) > 0) {
            return [
                'code' => 1,
                'mesg' => '密码不能含有3位以上的连续数字',
            ];
        }
        if (!preg_match('/^(.*[A-Za-z].*\d.*|.*\d.*[A-Za-z].*|.*[A-Z].*[a-z].*|.*[a-z].*[A-Z].*)$/', $Password)) {
            return [
                'code' => 1,
                'mesg' => '必须有大写字母，小写字母或数字中的两项',
            ];
        }
        return [
            'code' => 0,
            'mesg' => 'success',
        ];

    }
}