<?php
declare(strict_types=1);

namespace Project\Demo\Controller;

use Phalcon\Mvc\Controller;

class BaseController extends Controller
{
    protected function distribute(array $methods, $synHandle)
    {
        // $this->rcache = $this->di['cache']->load($this->di['config']->path('storages.redis')->toArray());
        // $serviceUrl = $this->di['config']->path('service.url');

        // \Yar_Concurrent_Client::reset();
        // foreach ($methods as $call)
        // {
            // \Yar_Concurrent_Client::call(
                // $serviceUrl,
                // $call['method'],
                // $call['arguments'],
                // $call['isParallel'] ? [$this, 'asynHandle'] : null,
                // [$this, 'asynError'],
                // $call['options']
            // );
        // }
        // \Yar_Concurrent_Client::loop([$this, $synHandle]);
    }

    public function asynHandle($asynResult, $asynCall)
    {
        //echo $asynCall['method'].': '.$asynResult, '<br />';
    }

    public function asynError($type, $asyncError, $asyncCall)
    {
        //$this->di->get('logger')->excludeAdapters(['cache'])->error("type: $type, method: {$asyncCall['method']}, $asyncError");
    }
}
?>