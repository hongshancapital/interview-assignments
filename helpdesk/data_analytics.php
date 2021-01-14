<?php

$data_analytics = new data_analytics('./Helpdesk_interview_data_set');

class data_analytics
{
    public $log    = null;
    public $combine_log = [];
    public $combine_key = -1;
    public $count_table = [];
    public $combine_pid = true; //是否集合错误进程的PID
    public $stage_2_arr = [];
    public $stage_3_key = 0;
    public $api_server = 'https://foo.com/bar';
    public $save_path  = './result_pid_combine.log';
    public $save = true; // 把结果保存在本地
    public $send = false; // 发送到服务器

    /**
     * 初始化，并处理
     * $path | string | 日志文件路径
     */
    public function __construct($path)
    {
        if (!file_exists($path)) {
            exit('File not found');
        }
        $this->read_log($path);
        $this->stage_1();
        $this->stage_2();
        $this->stage_3();
        $this->stage_4();
    }

    /**
     * 读取日志内容
     */
    public function read_log($path)
    {
        $this->log = file($path);
    }

    /**
     * 第一阶段，规整数据，合并日志中的多行数据。
     */
    public function stage_1()
    {
        foreach ($this->log as $line) {
            $this->line_combine($line);
        }
        $this->log = $this->combine_log;
    }

    /**
     * 第二阶段，拆分数据并写入到数组
     */
    public function stage_2()
    {
        foreach ($this->log as $line) {
            $tmp = explode(' ', $line);
            $matche = preg_match_all("|\((.*)\[(\d.*)\]\)\: (.*)|", $line, $process);
            if ($matche > 0) {
                $this->stage_2_arr[] = [
                    "deviceName" => $tmp[3],
                    "processId" => $process[2][0],
                    "processName" => $process[1][0],
                    "description" => $process[3][0],
                    "time" => "{$tmp[2]}"
                ];
            }
        }
    }

    /**
     * 第三阶段，结算
     */

    public function stage_3()
    {

        foreach ($this->stage_2_arr as $unit) {

            //窗口时间
            $time_tmp    = (int)substr($unit['time'], 0, 2);
            $time_hour1  = $time_tmp;
            $time_hour2  = ($time_tmp + 1) > 23 ? 0 : $time_tmp + 1;
            $time_format1 = $this->time_formater($time_hour1);
            $time_format2 = $this->time_formater($time_hour2);
            $time_window = "{$time_format1}-{$time_format2}";

            //创建唯一索引 - 进程名称 - 进程号 - 错误描述，
            if ($this->combine_pid) {
                $index = "{$unit['processName']}{$unit['description']}{$time_window}";
            } else {
                $index = "{$unit['processName']}{$unit['processId']}{$unit['description']}{$time_window}";
            }

            //尚未建立这个错误的统计
            if (!isset($this->count_table[$index])) {
                $this->count_table[$index] = [
                    "deviceName"  => $unit['deviceName'],
                    "processName" => $unit['processName'],
                    "description" => $unit['description'],
                    'timeWindow'  => $time_window,
                    'numberOfOccurrence' => 1,
                ];
                //开启PID集合的时候
                if ($this->combine_pid) {
                    $this->count_table[$index]['processId'][] = $unit['processId'];
                } else {
                    $this->count_table[$index]['processId']   = $unit['processId'];
                }
                $this->stage_3_key++;
            } else {
                //已建立记录，计数器+1
                $this->count_table[$index]['numberOfOccurrence']++;
                //开启PID集合的时候
                if ($this->combine_pid) {
                    if (!in_array($unit['processId'], $this->count_table[$index]['processId'])) {

                        $this->count_table[$index]['processId'][] = $unit['processId'];
                    }
                }
            }
        }
    }

    /**
     * 第四阶段,重组数据并发送
     */
    public function stage_4()
    {
        $final_data = [];
        foreach ($this->count_table as $row) {
            $final_data[] = $row;
        }
        $data = json_encode($final_data);
        //发送到服务器
        if ($this->send) {
            $this->post_data($this->api_server, ['log' => $data]);
        }
        //写入到当前目录
        if ($this->save) {
            file_put_contents($this->save_path, $data);
        }
    }

    /**
     * 将时间处理成想要的格式
     */
    public function time_formater($int)
    {
        if ($int < 10) {
            return "0{$int}00";
        }
        return "{$int}00";
    }

    /**
     * 多行处理。起始行一定是可以格式化的时间字符串。
     */
    public function line_combine($line)
    {
        $line = trim($line);
        $tmp = explode(' ', $line);
        $matche = preg_match('|\d{2}:\d{2}:\d{2}|', $tmp[2]);
        $this->combine_key++;
        if ($matche === 1) {
            $this->combine_log[$this->combine_key] = $line;
            return false;
        } else {
            $this->combine_key--;
            $this->combine_log[$this->combine_key] .= $line;
            return true;
        }
    }

    /**
     * 发送数据
     */
    public function post_data($url, $data)
    {
        $send      = http_build_query($data);
        $opts      = array(
            'http' => array(
                'method'  => 'POST',
                'header'  => 'Content-type: application/x-www-form-urlencoded',
                'content' => $send,
            )
        );
        $cxContext = stream_context_create($opts);
        file_get_contents($url, false, $cxContext);
    }
}
