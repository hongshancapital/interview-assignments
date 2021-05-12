<?php

if (is_file(__DIR__ . '/vendor/autoload.php')) {
    require_once __DIR__ . '/vendor/autoload.php';
}

// 注册命名空间
spl_autoload_register(function ( $name ) {
    // 命名空间
    $class_path = str_replace('\\', DIRECTORY_SEPARATOR, $name);
    // 文件路径
    $class_file = __DIR__ . DIRECTORY_SEPARATOR . "$class_path.php";
    // 引入文件
    if (is_file($class_file)) {

        require_once $class_file;

        if (class_exists($name, false)) {
            return true;
        }
    }
    return false;
});

