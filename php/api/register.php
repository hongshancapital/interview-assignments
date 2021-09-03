<?php
require_once __DIR__ . '/../src/helper.php';
require_once __DIR__ . '/../src/db.php';

// 接收参数
$data = json_decode(@$GLOBALS['HTTP_RAW_POST_DATA'], true);
if (is_null($data)) {
    error('', '');
}

// 常规非空检查
if (!isset($data['username']) || trim($data['username']) == '') {
    error('username', 'Username 不能为空');
}
if (!isset($data['password']) || trim($data['password']) == '') {
    error('password', 'Password 不能为空');
}
if (!isset($data['repeat_password']) && trim($data['repeat_password']) == '') {
    error('repeat_password', 'Repeat Password 不能为空');
}

$username       = trim($data['username']);
$password       = trim($data['password']);
$repeatPassword = trim($data['repeat_password']);

## Username 校验
/**
 * 只能以英文字母或下划线开头
 * 只能包含英文字母，下划线或数字
 */
$times = preg_match_all('/^[a-z_][a-z0-9_]{0,15}$/i', $username);
if ($times === 0) {
    error('username', '用户名只能以英文字母或下划线开头且只能包含英文字母，下划线或数字，最长16位');
}

// 用户已存在校验
/** @var PDO $pdo */
$stmt = $pdo->prepare("select username from user where username = ?");
$stmt->execute(array($username));
$userList = $stmt->fetchAll();
if (count($userList) != 0) {
    error('username', '用户名已存在');
}


## Password 校验
$times = preg_match_all('/^[a-z0-9_-]*$/i', $password);
if ($times === 0) {
    error('username', '密码不规范');
}

//长度在 6 位以上
if (strlen($password) < 6 || strlen($password) > 32) {
    error('password', '密码长度应处于6-32位之间');
}

//不能含有 3 位或以上的连续数字，如 123、234 等
if (hasContinuousNumber($password)) {
    error('password', '不能含有 3 位或以上的连续数字');
}

//必须有大写字母，小写字母或数字中的两项
$matchTimes = preg_match('/[A-Z]/', $password) + preg_match('/[a-z]/', $password) + preg_match('/[0-9]/', $password);
if ($matchTimes < 2) {
    error('password', '必须有大写字母，小写字母或数字中的两项');
}

## Repeat Password 校验
if ($password != $repeatPassword) {
    error('repeat_password', 'Repeat Password 与 Password 不一致');
}


// 写入数据库
$stmt     = $pdo->prepare("insert into user(`username`,`password`,`created_at`,`updated_at`) values(?,?,?,?)");
$datetime = date('Y-m-d H:i:s');
$res      = $stmt->execute(array($username, md5($password . PASSWORD_SLOT), $datetime, $datetime));

success();

