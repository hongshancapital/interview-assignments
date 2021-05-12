<?php

namespace app\common;

use origin\Log;
use origin\Response;

class Route
{
    private $route = [
        '\register' => '\app\Register',
    ];
    /**
     * 服务启动
     * @author decezz@qq.com
     * @return void
     */
    public function run()
    {
        try {
            Log::instance('log', __DIR__ . '/../../log/applog.log');
            $service = $_GET['s'];
            if (empty($service)) {
                throw new \Exception("Service not find", 4001);
                $this->response(500, 'Service not find');
            }
            // 命名空间
            $class = str_replace('/', '\\', $service);
            $class = $this->route[$class];
            if (!class_exists($class)) {
                $this->response(500, 'Service not find');
            }
            // 服务执行
            (new $class())->run();
        } catch (\Exception $e) {
            Log::error(sprintf('Exception code [%s] msg : %s', $e->getCode(), $e->getMessage()));
            $this->response(500, $e->getMessage());
        }
    }

    /**
     * 响应
     * @author decezz@qq.com
     * @param int $code
     * @param string $content
     * @return mixed
     */
    private function response(int $code, string $content)
    {
        (new Response())->code($code)->content($content)->send();
    }
}
