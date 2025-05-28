<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改密码</title>
	</head>
	<style type="text/css">
		.el-input{
			width: 67%;
		}
		.el-textarea{
			width: 67%;
		}
		.el-textarea__inner{
			min-height: 20%;
		}
		.el-form-item__label{
			font-size: 1.6rem;
		}
	</style>
<body>
	<div id="app" style="width: 1200px;margin:0 auto;padding-top: 14px;margin-bottom: 20px;">
		<el-form label-width="25%" :model="ruleForm" status-icon ref="ruleForm" class="demo-ruleForm">
			<div class="grid-content" style="text-align:center;font-size: 30px;margin-bottom: 4%;">
				注册
			</div>
			<div>
				<el-form-item label="用户名" prop="Username">
					<el-input type="text" v-model="ruleForm.Username" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="Password">
					<el-input type="password" v-model="ruleForm.Password" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item>
					<el-checkbox-group v-model="ruleForm.RepeatPassword">
						<el-checkbox label="记住密码" name="RepeatPassword"></el-checkbox>
					</el-checkbox-group>
				</el-form-item>
				<div style="text-align:center;">
					<el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
				</div>
			</div>
		</el-form>
	</div>
</body>


<script>
	
	var self=new Vue({
		el: '#app',
		data: function() {
			return {
				ruleForm: {
					Username: '',
					Password: '',
					RepeatPassword: true,
				},
			};
		},
		methods: {
			submitForm(formName) {
				var me = this;
				this.$refs[formName].validate((valid) => {
					axios.post('/v1/api/register', {
						Username:self.ruleForm.Username,
						Password:self.ruleForm.Password,
						RepeatPassword:self.ruleForm.RepeatPassword,
					})
					.then(function (response) {
						if (response.data.code == 0) {
							me.$alert('注册成功', '提示', {
								confirmButtonText: '确定',
							})
							.then(() => {//点击确定之后跳转
								window.location.href="https://www.baidu.com";
							})
							.catch(() => {//点击右上角×之后跳转
								window.location.href="https://www.baidu.com";
							});
						}else{
							me.$alert(response.data.items, '提示', {
								confirmButtonText: '确定',
							});
							return false;
						}
					})
					.catch(function (error) {
					 	console.log(error);
					});
					
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			},
		},
	})
</script>
</html>