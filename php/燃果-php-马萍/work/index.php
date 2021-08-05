<?php
/**
 * Created by PhpStorm.
 * User: maping
 * Date: 2021/8/5
 * Time: 上午9:57
 */
class Start{
    public $http_server;
    public function __construct()
    {
        $this->http_server = new swoole_http_server('0.0.0.0','3000');
        $this->http_server->set([
            'enable_static_handler'=>true,
            'document_root'=>__DIR__.'/view'
        ]);
        $this->http_server ->on('request', [$this,'onRequest']);
        $this->http_server ->start();
    }


    public function onRequest($request,$response)
    {
        if ($request->server['path_info'] == '/favicon.ico' || $request->server['request_uri'] == '/favicon.ico') {
            $response->end();
            return;
        }
        list($controller, $action) = explode('/', trim($request->server['request_uri'], '/'));
        if ($controller == '')
        {
            $html = file_get_contents('./view/index.html');
            $response->end($html);
        }

        $class = '\api\\' . ucfirst($controller) . "Controller";
        $obj = new $class();
        $res = $obj->$action($request);
        $response->end($res);

    }
}

//自动加载
function autoload($classname) {
    $class = str_replace('\\', '/', $classname);
    include $class . '.php';
}
spl_autoload_register('autoload');

//开始
$obj = new Start();
