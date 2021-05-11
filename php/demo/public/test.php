<?php
$password = $_GET['a'] ?? '';
if (preg_match("/[\w]*[\d]{3,}[\w]*$/", $password)) {
	echo 1;
} else {
	echo 0;
}