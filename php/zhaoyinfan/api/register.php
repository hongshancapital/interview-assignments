<?php

require '../Model/UserList.php';
require '../Service/Base.php';
require '../Service/User.php';

/**
 * @Author   : zhaoyinfan
 * @Date     : 2021-04-16 17:50
 * @Describe : 注册信息验证&&保存
 */

$userName       = Base::getParams('userName');
$password       = Base::getParams('password');
$repeatPassword = Base::getParams('repeatPassword');

$checkUserName     = User::checkUserName($userName);
$checkUserNameCode = $checkUserName['code'] ?? 0;
if (1000 != $checkUserNameCode) {
    echo json_encode($checkUserName, JSON_UNESCAPED_UNICODE);
    exit;
}

$checkPassword     = User::checkPassword($password);
$checkPasswordCode = $checkPassword['code'] ?? 0;
if (1000 != $checkPasswordCode) {
    echo json_encode($checkPassword, JSON_UNESCAPED_UNICODE);
    exit;
}

$checkRepeatPassword     = User::checkRepeatPassword($password, $repeatPassword);
$checkRepeatPasswordCode = $checkRepeatPassword['code'] ?? 0;
if (1000 != $checkRepeatPasswordCode) {
    echo json_encode($checkRepeatPassword, JSON_UNESCAPED_UNICODE);
    exit;
}

//数据保存ok 跳转
$params     = [
    'user_name'     => $userName,
    'user_password' => md5($password),
];
$userObject = new UserList();
$userInfo   = $userObject->getName($userName);
if ($userInfo) {
    $rt = $userObject->updateData(['user_name' => $userName], ['user_password' => $params['user_password']]);
}

$rt             = $userObject->createData($params);
$returnDataOk   = [
    'code'    => 1000,
    'message' => "success",
];
$returnDataFail = [
    'code'    => 1001,
    'message' => "注册失败，请稍后重试",
];
$returnData     = $rt == true ? $returnDataOk : $returnDataFail;
echo json_encode($returnData, JSON_UNESCAPED_UNICODE);