<?php
declare (strict_types=1);

/**
 * 开发: Atom
 * 时间: 2021-05-15
 */

namespace app\middleware;


/**
 * 注册前中间件
 * 用于业务规则设计
 * Class RegisterCheck
 * @package app\middleware
 */
class RegisterCheck
{
    public function handle($request, \Closure $next)
    {
        return $next($request);
    }
}