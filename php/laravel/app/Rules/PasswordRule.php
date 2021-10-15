<?php


namespace App\Rules;


use Illuminate\Contracts\Validation\Rule;
use Illuminate\Support\Str;

class PasswordRule implements Rule
{
    private string $message;

    public function passes($attribute, $value) :bool
    {
        if (preg_match('/(\d(?:(?<=0)1|(?<=1)2|(?<=2)3|(?<=3)4|(?<=4)5|(?<=5)6|(?<=6)7|(?<=7)8|(?<=8)9){2,}|\d(?:(?<=9)8|(?<=8)7|(?<=7)6|(?<=6)5|(?<=5)4|(?<=4)3|(?<=3)2|(?<=2)1|(?<=1)0){2,})/', $value)) {
            $this->message = '不能含有 3 位或以上的连续数字';
            return false;
        }

        $strArr = str_split($value);
        $hasStrLower = 0;
        $hasStrUpper = 0;
        $hasNumber = 0;
        foreach ($strArr as $str) {
            if (($hasStrUpper + $hasStrLower + $hasNumber) === 2) {
                break;
            }

            if (is_numeric($str)) {
                $hasNumber = 1;
            }

            if (($hasStrLower + $hasStrUpper) < 2 && is_string($str)) {
                $ord = ord($str);
                if ($hasStrUpper === 0 && $ord >= 65 && $ord <= 90) {
                    $hasStrUpper = 1;
                }

                if ($hasStrLower === 0 && $ord >= 97 && $ord <= 122) {
                    $hasStrLower = 1;
                }
            }
        }

        if (($hasStrUpper + $hasStrLower + $hasNumber) < 2) {
            $this->message = '必须有大写字母，小写字母或数字中的两项';
            return false;
        }

        return true;
    }

    public function message(): string
    {
        return $this->message;
    }
}
