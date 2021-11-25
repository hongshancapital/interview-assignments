<?php

use yii\db\Migration;

/**
 * Class m210906_035020_user
 */
class m210906_035020_user extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $tableOptions = null;
        if ($this->db->driverName === 'mysql') {
            // http://stackoverflow.com/questions/766809/whats-the-difference-between-utf8-general-ci-and-utf8-unicode-ci
            $tableOptions = 'CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci ENGINE=InnoDB COMMENT "用户表"';
        }

        $this->createTable('{{%user}}', [
            'id' => $this->primaryKey(),
            'Username' => $this->string()->notNull()->comment('用户名'),
            'Password'=> $this->string(32)->notNull()->comment('密码'),
            'Salt' => $this->string(6)->notNull()->comment('盐'),
            'status' => $this->tinyInteger(1)->defaultValue(0)->comment('用户状态'),//0 禁止 1 激活
            'created_at' => $this->integer()->notNull()->comment('注册日期'),
            'updated_at' => $this->integer()->notNull()->comment('最近一次修改日期'),
        ], $tableOptions);
    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $this->dropTable('{{%user}}');
    }
}
