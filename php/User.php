<?php
namespace app\models;

use yii;
use yii\base\Model;
use yii\db\Query;

class User extends Model
{

    private $tb = 'user';

    //状态 10：正常 20：禁用
    const STATUS_OK      = '10';
    const STATUS_AUDIT_PASS   = '20';


    /**
     * Method add 写入注册数据
     * @param $params
     * @return array
     * @throws yii\db\Exception
     */
    public function add ($params)
    {
        $result = [];

        $time = date('Y-m-d H:i:s');
        $indata = [
            'user_name' => $params['user_name'],
            'password' => $this->createPassword($params['password']),
            'status' => self::STATUS_OK,
            'create_time' => $time,
            'update_time' => $time,
        ];
        // 开始写入数
        $connection = Yii::$app->db;
        $do = $connection->createCommand()->insert($this->tb, $indata)->execute();
        $commentId = $connection->getLastInsertID();
        if (!$do) {
            return [];
        }

        $result['user_id'] = $commentId;

        return $result;
    }

    /**
     * 查询该用户名是否 已注册
     * Method get_name
     * @param $userName
     * @return mixed
     */
    public function check_user_name($userName)
    {
        $query = new Query();
        $comment = $query->select(['*'])
            ->from($this->tb)
            ->where(['name' => $userName])
            ->one();
        if (!empty($comment)) {
            return true;
        }
        return false;
    }

    /**
     * Method createPassword 加密用户密码
     * @param $str
     * @return false|string|null
     */
    private function createPassword ($str)
    {
        return password_hash($str.'_'.USER_PASSWORD_KEY, PASSWORD_BCRYPT, ['cost' => USER_PASSWORD_COST]);
    }

}