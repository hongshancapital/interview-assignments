<?php
require_once __DIR__ . './config.php';

$dsn = "mysql:host=" . DB_HOST . ";dbname=" . DB_NAME;
try {
    $pdo = new PDO($dsn, DB_USER, DB_PWD); //初始化一个PDO对象
} catch (PDOException $e) {
    die ("Error!: " . $e->getMessage() . "<br/>");
}
