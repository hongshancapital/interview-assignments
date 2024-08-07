<?php

/**
 * 是否包含连续的数字
 * @param string $str 字符串
 * @param int $maxCount 最大连续的数字
 * @return bool
 */
function hasContinuousNumber($str, $maxCount = 2)
{
    $arr        = str_split($str);
    $currentNum = 1;
    $length     = count($arr);
    for ($i = 1; $i < $length; $i++) {
        // 当前值比上一个值大一
        if (($arr[$i] - $arr[$i - 1]) == 1) {
            $currentNum++;
        } else {
            $currentNum = 1;
        }

        if ($currentNum > $maxCount)
            return true;
    }

    return false;
}


function success()
{
    echo json_encode([
        'code'    => 200,
        'message' => "请求成功",
        'data'    => []
    ]);
    die;
}

function error($field, $waringInfo)
{
    echo json_encode([
        'code'    => 401,
        'message' => "参数错误",
        'data'    => [
            'field'        => $field,
            'warning_info' => $waringInfo
        ]
    ]);
    die;
}