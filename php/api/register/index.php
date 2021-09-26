<?php
/**
	数据库
	 CREATE TABLE `user` (
	  `id` int NOT NULL AUTO_INCREMENT,
	  `username` varchar(20) NOT NULL DEFAULT '',
	  `pw` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
	  `date` datetime DEFAULT NULL,
	  PRIMARY KEY (`id`),
	  UNIQUE KEY `username` (`username`)
	) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

	nginx
	server {
        listen       3000;
        #server_name  www.ums.com;
        root   /var/web/ms/php;
        location / {
            index  index.php index.html index.htm;
        }
        location ~ \.php$ {
			fastcgi_pass   127.0.0.1:9000;
			fastcgi_index  index.php;
           	fastcgi_param  SCRIPT_FILENAME  $document_root$fastcgi_script_name; 
    		include        /usr/local/etc/nginx/fastcgi_params;

        }
    }
*/
	$username = $_POST["username"];
	$pw = $_POST["pw"];
	$preg = preg_match("/^[a-zA-Z_][a-zA-Z_0-9]*$/",$username);
	$msg = '';
	$rel = [
		"code" => 1,
	];
	if(!$preg){
		$msg = '用户名只能以英文字母或下划线开头,只能包含英文字母，下划线或数字';
		$rel["code"] = -1;
	}
	$pw_len = strlen($pw);
	$pw_preg = preg_match("/(0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){2}((?<=0)1|(?<=1)2|(?<=2)3|(?<=3)4|(?<=4)5|(?<=5)6|(?<=6)7|(?<=7)8|(?<=8)9){1}/", $pw);
	if($pw_preg){
		$msg .= " 密码不能包含3位连续数字";
		$rel["code"] = -1;
	}
	$pw_preg = preg_match("/(?![a-z]+$)(?![0-9]+$)(?![A-Z]+$)[a-zA-Z0-9]{6}$/", $pw);
	if(!$pw_preg){
		$msg .= " 密码必须有大写字母，小写字母或数字中的两项且长度不能小于6位";
		$rel["code"] = -1;
	}
	if($rel["code"]<0){
		$rel["msg"] = $msg;
		echo json_encode($rel);exit;
	}
	$mysqli = mysqli_connect("localhost", "root", "root","ms");
	$time = date("Y-m-d H:i:s");
	$pw = md5($pw);
	$sql = "insert user (username,pw,date) values('".$username."','".$pw."','".$time."')";

	$result = $mysqli->query($sql);
	$rel["msg"] = "操作成功";
	if(!$result){
		$rel["msg"] = "数据添加失败";
	}
	echo json_encode($rel);
?>