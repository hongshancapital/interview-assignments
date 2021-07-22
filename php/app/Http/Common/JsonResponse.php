<?php


namespace App\Http\Common;

use App\Listeners\CacheEventListener;


trait JsonResponse
{
    
    public function success($data)
    {
        $success = [
            'trace_id'    => traceId(),
            'error_code'  => 0,
            'error_msg'   => 'success',
            'server_time' => time(),
            'data'        => is_null($data) ? (object)[] : $data,
        ];

        $success += $this->debugQuery();
        $success += $this->debugCacheQuery() ;
        return response()->json($success)->setEncodingOptions(JSON_UNESCAPED_UNICODE);
    }

    
    public function error($errmsg, $errcode = 1, $statusCode = 200)
    {
        $error = [
            'trace_id'    => traceId(),
            'error_code'  => (int)$errcode,
            'server_time' => time(),
            'error_msg'   => $errmsg,
        ];
        $error += $this->debugQuery();
        $error += $this->debugCacheQuery() ;
        return response()->json($error)->setEncodingOptions(JSON_UNESCAPED_UNICODE)->setStatusCode($statusCode);

    }
    /**
     * debug sql query log
     *
     * @return array
     */
    private function debugQuery()
    {
        return config('app.debug') ? ['query' => \DB::getQueryLog()] : [];
    }

    /**
     * cache Log from
     * @return array
     */
    private function debugCacheQuery()
    {
        return config('app.debug') ? ['cache_query' => CacheEventListener::getCacheQueryLog()] : [];
    }
}
