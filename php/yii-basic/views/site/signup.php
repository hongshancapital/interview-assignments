<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\SignupForm */
/* @var $form ActiveForm */
?>

<script type="text/javascript">

</script>


<div class="site-signup">


    <form id="signup-form">
        <div class="form-group">
            <label for="username" class="control-label">Username</label>
            <input type="text" id="username" class="form-control" name="username">
        </div>
        <div class="form-group">
            <label for="password" class="control-label">Password</label>
            <input type="password" id="password" class="form-control" name="password">
        </div>
        <div class="form-group">
            <label for="repeat_password" class="control-label">Repeat Password</label>
            <input type="password" id="repeat_password" class="form-control" name="repeat_password">
        </div>
        <button class="btn btn-primary" type="button" id="submit">Submit</button>

        <div id="err-text" class="text-danger" style="text-align: center;padding: 20px;" ></div>
    </form>


<!--这里是不使用API的写法-->
<!--    --><?php //$form = ActiveForm::begin(); ?>
<!--        --><?//= $form->field($model, 'username') ?>
<!--        --><?//= $form->field($model, 'password') ?>
<!--        --><?//= $form->field($model, 'repeat_password') ?>
<!--        <div class="form-group">-->
<!--            --><?//= Html::submitButton('Submit', ['class' => 'btn btn-primary']) ?>
<!--        </div>-->
<!--    --><?php //ActiveForm::end(); ?>
</div>

<?php
$js = <<<JS
    $(document).ready(function(){
        $("#submit").on("click",function(){
            $.post('/api/register',$('#signup-form').serialize(),function(result){
                if (result.code == 200) {
                    $('#err-text').text('注册成功')
                }else{
                    $('#err-text').text(result.message)
                }
            })
        });
    });
JS;
$this->registerJs($js,\yii\web\View::POS_END);
