<?php
/**
 * Created by PhpStorm.
 * User: maping
 * Date: 2021/8/5
 * Time: 上午10:58
 */
namespace api;
class Controller
{

    public function returnSuccess($data,$msg='',$status=200,$code='')
    {
        if ($code == '')
        {
            $code = $status;
        }
        return json_encode(['data'=>$data,'code'=>$code,'msg'=>$msg,'status'=>$status]);
    }


    public function returnError($msg='',$code=400)
    {
        return json_encode(['code'=>$code,'msg'=>$msg,'status'=>$code]);
    }

}