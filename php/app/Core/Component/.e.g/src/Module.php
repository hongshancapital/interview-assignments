<?php
/**
 * Module 注册文件
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Demo;

use Phalcon\Di\DiInterface;
use Phalcon\Mvc\ModuleDefinitionInterface;

class Module implements ModuleDefinitionInterface
{
    public function registerAutoloaders(DiInterface $di = null)
    {
        //$di['loader']->registerNamespaces([
        //    'Admin' => __DIR__ . '/Controller/Admin',
        //    'Api' => __DIR__ . '/Controller/Api'
        //], true)->register();
    }

    public function registerServices(DiInterface $di)
    {
        //$di['view']->setViewsDir(__DIR__ . '/View');
    }
}

?>