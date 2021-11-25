<?php


namespace app\tools;


class Utility
{
    /**
     * 生成随机字串
     *
     * @param integer $min
     *        	起始长度.
     * @param integer $max
     *        	最大长度.
     * @return string 返回一个随机字符串
     */
    public static function genRandStr($min, $max) {
        if (is_int ( $max ) && $max > $min) {
            $min = mt_rand ( $min, $max );
            $output = '';
            for($i = 0; $i < $min; $i ++) {
                $which = mt_rand ( 0, 2 );
                if ($which === 0) {
                    $output .= mt_rand ( 0, 9 );
                } elseif ($which === 1) {
                    $output .= chr ( mt_rand ( 65, 90 ) );
                } else {
                    $output .= chr ( mt_rand ( 97, 122 ) );
                }
            }
            return $output;
        }
        return false;
    }
}