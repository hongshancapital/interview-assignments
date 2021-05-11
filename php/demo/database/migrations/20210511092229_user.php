<?php

use think\migration\Migrator;
use think\migration\db\Column;

class User extends Migrator
{
    /**
     * Change Method.
     *
     * Write your reversible migrations using this method.
     *
     * More information on writing migrations is available here:
     * http://docs.phinx.org/en/latest/migrations.html#the-abstractmigration-class
     *
     * The following commands can be used in this method and Phinx will
     * automatically reverse them when rolling back:
     *
     *    createTable
     *    renameTable
     *    addColumn
     *    renameColumn
     *    addIndex
     *    addForeignKey
     *
     * Remember to call "create()" or "update()" and NOT "save()" when working
     * with the Table class.
     */
    public function change()
    {
        $table  =  $this->table('user', array('engine'=>'INNODB', 'collation'=>'utf8_bin'));
        $table->addColumn('username', 'string', array('limit'=>100,'default'=>NULL, 'comment'=>'用户名，登陆使用'))
	        ->addColumn('password', 'string', array('limit'=>32, 'default'=>NULL, 'comment'=>'用户密码')) 
	        ->addColumn('salt', 'string', array('limit'=>32, 'default'=>NULL, 'comment'=>'用户密码混淆'))
	        ->addColumn('created', 'integer', array('default'=>0, 'comment'=>'注册时间'))
	        ->create(); 
    }
}
