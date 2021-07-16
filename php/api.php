<?php

include_once 'db.php';

$action = empty($_GET['action']) ? '' : doStr($_GET['action']);

switch ($action) {
    case 'register':
        $username = empty($_POST['username']) ? '' : doStr($_POST['username']);
        $password = empty($_POST['password']) ? '' : doStr($_POST['password']);

        if (!$username || !$password) {
            echoJson('参数错误');
        }

        if (!preg_match("/^[a-zA-Z_]([-_a-z0-9]+)$/i", $username)) {
            echoJson('username 只能以英文字母或下划线开头，且只能包含英文字母，下划线或数字', 111);
        }

        if (!preg_match("/([a-z0-9]{6,})$/i", $password)) {
            echoJson('password 必须有大写字母，小写字母或数字中的两项，长度在 6 位以上', 112);
        }

        if (preg_match("/\d{4}/", $password)) {
            echoJson('password 必须不能含有 3 位以上的连续数字', 113);
        }

        $sql = sprintf("INSERT INTO `user`(`username`,`password`) VALUES('%s', '%s')", $username, md5($password));
        if ($db->query($sql)) {
            echoJson('注册成功');
        }

        echoJson('注册失败', 100);
        break;
    default:
        echo '404';
        break;
}

function doStr($str) {
    if (version_compare(PHP_VERSION, '7.2.34', '<')) {
        !get_magic_quotes_gpc() && $str = addslashes($str);
    } else {
        $str = addslashes($str);
    }

    $str = htmlspecialchars($str);

    return (string)$str;
}

function echoJson($msg, $errcode = 0, $append_array = []) {
    header('content-type:application/json;charset=utf-8');
    $result = ['errcode' => $errcode, 'msg' => $msg];
    $append_array && $result += $append_array;

    echo json_encode($result);
    exit;
}