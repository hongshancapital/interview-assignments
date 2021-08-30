<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>register</title>
</head>
<body>
<div id="center">
	<form name="myform" enctype="multipart/form-data" action="" method="post" >
		<div>
			<span>用&nbsp;&nbsp;户&nbsp;&nbsp;名：</span>
			<input class="form-control" name="username" type="text" value="" placeholder="只能输入字母数字下划线"/>
		</div>
		<div>
			<span>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
			<input class="form-control" name="password" type="password" value=""  placeholder="密码最小长度6位"/>
        </span>
		</div>
		<div>
			<span>确认密码：</span><input class="form-control" name="repassword" type="password" value=""/>
        </span>
		</div>
		<div>
			<input name="registerButton"  class="btn btn-primary" type="button" id="submit" value="注册">
		</div>
	</form>
</div>
<script src="/static/js/jquery.min.js"></script>
<script>
	(function () {
		let submit_stat = true;
		$("#submit").click(function () {
			if (!submit_stat) return false;
			submit_stat = false;
			let username = $('input[name="username"]').val();
			let password = $('input[name="password"]').val();
			let repassword = $('input[name="repassword"]').val();
			// 前端暂时取消验证，使用api验证
			/*// 验证用户名
			let u_reg = /^[_a-zA-Z][_a-zA-Z0-9]*$/;
			if (!u_reg.test(username)) {
				alert('用户名只能是字母下划线开头，包含字母下划线数字');
				submit_stat = true;
				return false;
			}
			// 验证密码
			let p_reg_1 = /^[0-9a-zA-Z]{6,}$/;
			let p_reg_2 = /[0-9]/;
			let p_reg_3 = /[a-z]/;
			let p_reg_4 = /[A-Z]/;
			if (!p_reg_1.test(password)) {
				alert('密码必须是6位以上包含数字大小写字母的字符');
				submit_stat = true;
				return false;
			}
			if (
					!((p_reg_2.test(password) && p_reg_3.test(password)) ||
					(p_reg_2.test(password) && p_reg_4.test(password)) ||
					(p_reg_3.test(password) && p_reg_4.test(password)))
			) {
				alert('密码必须含有“小写字母”、“大写字母”、“数字”中的任意两种');
				submit_stat = true;
				return false;
			}
			let p_reg_5 = /(012|123|234|345|456|678|789)/;
			if (p_reg_5.test(password)) {
				alert('不能含有 3 位或以上的连续数字');
				submit_stat = true;
				return false;
			}
			// 验证重复密码
			if (password != repassword) {
				alert('密码与确认密码不一致');
				submit_stat = true;
				return false;
			}*/
			$.ajax({
				url : '/index.php/api/register',
				type : 'post',
				dataType : "json",
				data : {
					username: username,
					password: password,
					repassword: repassword
				},
				success : function(res){
					if (res.code != 0) {
						alert(res.msg);
					} else {
						alert('注册成功');
					}
					submit_stat = true;
				},
				error : function(){
					alert('网络异常，请刷新重试');
					submit_stat = true;
				}
			});
		});
	})();
</script>
</body>
</html>
