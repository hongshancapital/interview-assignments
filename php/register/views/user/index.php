<?php

use yii\bootstrap4\ActiveForm;
use yii\bootstrap4\Html;

$this->title = '注册';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="site-login">
    <h1><?= Html::encode($this->title) ?></h1>
    <?php $form = ActiveForm::begin([
        'id' => 'reg-form',
        'layout' => 'horizontal',
        'fieldConfig' => [
            'template' => "{label}\n<div class=\"col-lg-3\">{input}</div>\n<div class=\"col-lg-8\">{error}</div>",
            'labelOptions' => ['class' => 'col-lg-2 col-form-label']
        ],
        'options'=>['onsubmit'=>'return ChkForm();']
    ]); ?>

    <?= $form->field($model, 'Username')->textInput(['autofocus' => true, 'placeholder'=>'请输入用户名'])->label('用户名') ?>
    <?= $form->field($model, 'Password')->passwordInput(['placeholder'=>'请输入密码'])->label('密码') ?>
    <?= $form->field($model, 'RepeatPassword')->passwordInput(['placeholder'=>'请输入密码'])->label('确认密码') ?>

    <div class="form-group">
        <div class="offset-lg-1 col-lg-11">
            <?= Html::submitButton('注册', ['class' => 'btn btn-primary', 'name' => 'login-button']) ?>
        </div>
    </div>

    <?php ActiveForm::end(); ?>
</div>
<script src="/js/userreg.js"></script>
<script type="text/javascript">
    //是否正在提交表单，防止用户多次提交
    var isSubmitting = false

    /**
     * @desc 提交表单前端校验
     * @returns {boolean}
     * @constructor
     */
    function ChkForm() {
        if (isSubmitting){
            return false
        }
        isSubmitting = true

        var username = $('#regform-username').val()
        var password = $('#regform-password').val()
        var password_repeat = $('#regform-repeatpassword').val()

        //校验用户名
        if (!username){
            alert('请输入用户名')
            $('#regform-username').focus()
            isSubmitting = false
            return false
        }
        if (!UserRegValidate.isUsername(username)){
            $('#regform-username').select()
            isSubmitting = false
            return false
        }
        //校验密码
        if (!password){
            alert('请输入密码')
            $('#regform-password').focus()
            isSubmitting = false
            return false
        }
        if (!UserRegValidate.isPassword(password)){
            $('#regform-password').select()
            isSubmitting = false
            return false
        }
        if (!password_repeat){
            alert('请输入校验密码')
            $('#regform-repeatpassword').focus()
            isSubmitting = false
            return false
        }
        if (password !== password_repeat){
            alert('两次密码输入不一致')
            $('#regform-password').select()
            isSubmitting = false
            return false
        }
        $.ajax({
            url : '/api/register',
            type : 'post',
            dataType : 'json',
            data : $('#reg-form').serialize(),
            success : function (response) {
                if (response.code == 200){
                    alert('注册成功')
                    window.location.reload()
                }else{
                    alert(response.msg)
                    isSubmitting = false
                }
            },
            error : function () {
                alert('网络错误，请重试')
                isSubmitting = false
            }
        })
        return false
    }
</script>