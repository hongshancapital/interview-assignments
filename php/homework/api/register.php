<?php
require_once '../models/BaseModel.php';

class register{
    public $username;
    public $password;
    public $con;

    public function do_register($input){
        $this->check_input($input);
        $this->check_exists();
        $this->add_user();
    }

    /**
     * @throws Exception
     */
    public function check_input($input){
        $username = $input['username'];
        $password = $input['password'];
        if (preg_match('/\W/', $username)){
            throw new Exception('用户名只能包含英文字母、下划线或数字');
        }
        if (is_numeric($username[0])){
            throw new Exception('用户名只能以英文字母、或下划线开头');
        }
        if (strlen($password)<=6){
            throw new Exception('密码长度必须在6位以上');
        }
        if ($password != $input['password_conf']){
            throw new Exception('两次密码输入内容不相同，请重新输入');
        }
        foreach(['012','123','234','345','456','567','678','789','890'] as $nums){
            if(strpos($password, $nums) > 0 || strpos($password, $nums) === 0){
                throw new Exception('密码中不能含有3位或以上的连续数字');
            }
        }
        $check_pwd_AZ = preg_match('/[A-Z]+/', $password);
        $check_pwd_az = preg_match('/[a-z]+/', $password);
        $check_pwd_09 = preg_match('/[0-9]+/', $password);
        if (($check_pwd_AZ + $check_pwd_az + $check_pwd_09) < 2){
            throw new Exception('密码必须包含大写字母、小写字母或数字中的两项');
        }
        $this->username = $username;
        $this->password = $password;
    }

    public function check_exists(){
        try {
            $this->con = \BaseModel::getCon();
        } catch (Exception $e) {
            throw new Exception($e->getMessage());
        }
        $sql = "select id from users where username = '$this->username';";
        $rows = $this->con->query($sql)->fetch_assoc();
        if (!empty($rows)){
            throw new Exception('用户名已注册，请修改用户名或选择登录');
        }
    }

    public function add_user(){
        $now = date('Y-m-d H:i:s', time());
        $sql = 'insert into users (username, password, created_at, updated_at, version) values ("'
            .$this->username.'","'.md5($this->password).'","'.$now.'","'.$now.'",0);';
        $res = $this->con->query($sql);
        if (!$res){
            throw new Exception('注册失败，请稍后再试');
        }
    }
}

try{
    (new register())->do_register($_REQUEST);
    echo json_encode(['status'=>'success']);
}catch (Exception $e){
    echo json_encode(['status'=>'fail', 'message'=>$e->getMessage()]);
}
?>
