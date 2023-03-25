<?php

try {
    $username = trim($_POST['username']) ?? "";
    $password = trim($_POST['password']) ?? "";
    $repeatPassword = trim($_POST['repeat_password']) ?? "";

    checkUsername($username);
    checkPassword($password, $repeatPassword);

    // 判断用户名是否重复
    $pdo = new PDO('mysql:host=localhost;dbname=test', 'root', 'root');
    $querySql = "SELECT id FROM user WHERE username = ? LIMIT 1";
    $queryStmt = $pdo->prepare($querySql);
    $queryStmt->bindValue(1, $username);
    $queryStmt->execute();
    if ($row = $queryStmt->fetch(PDO::FETCH_ASSOC)) {
        echo '用户名重复';
        return;
    }

    // 用户名密码插入数据库
    $insertSql = "INSERT INTO user (username, password) VALUE (?, ?)";
    $insertStmt = $pdo->prepare($insertSql);
    $insertStmt->bindValue(1, $username);
    $insertStmt->bindValue(2, sha1($password));
    $insertStmt->execute();
    $insertId = $pdo->lastInsertId();
    if ($insertId) {
        echo "注册成功";
        return;
    }

    echo "注册失败";
} catch (Exception $e) {
    echo $e->getMessage();
}

/**
 * 校验用户名
 * @param $username
 * @return null
 * @throws Exception
 */
function checkUsername($username)
{
    if (empty($username)) {
        throw new Exception("用户名不能为空");
    }

    if (!preg_match("/^[a-zA-Z_][a-zA-Z0-9_]*$/", $username)) {
        throw new Exception("用户名只能以英文字母或下划线开头并且只能包含英文字母，下划线或数字");
    }
    return null;
}

/**
 * 校验密码
 * @param $password
 * @param $repeatPassword
 * @throws Exception
 */
function checkPassword($password, $repeatPassword)
{
    if (empty($password) || empty($repeatPassword)) {
        throw new Exception("密码不能为空");
    }

    if ($password != $repeatPassword) {
        throw new Exception("两次密码输入不一样");
    }

    if (strlen($password) < 6) {
        throw new Exception("密码长度需在6位以上");
    }

    if (preg_match('/[\w*][0-9]{3,}[\w]*/', $password)) {
        throw new Exception("密码不能含有 3 位以上的连续数字");
    }

    $numberNum = preg_match('/[\w*][0-9]+[\w]*/', $password);
    $smallLetterNum = preg_match('/[\w*][a-z]+[\w]*/', $password);
    $bigLetterNum = preg_match('/[\w*][A-Z]+[\w]*/', $password);
    if (!(($numberNum && $smallLetterNum) || ($numberNum && $bigLetterNum) || ($bigLetterNum && $smallLetterNum))) {
        throw new Exception("密码必须有大写字母，小写字母或数字中的两项");
    }
}
