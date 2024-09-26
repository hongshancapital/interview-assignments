package main

import (
	"bufio"
	"bytes"
	"crypto/md5"
	"encoding/hex"
	"encoding/json"
	"errors"
	"flag"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"regexp"
	"strings"
	"time"
)

type LogFormat struct {
	DeviceName         string
	ProcessId          string
	ProcessName        string
	Description        string
	TimeWindow         string
	NumberOfOccurrence int
}

var logStats map[string]*LogFormat
// 时间窗定义
var time_windows = map[string]string{
	"00": "0000-0100",
	"01": "0100-0200",
	"02": "0200-0300",
	"03": "0300-0400",
	"04": "0400-0500",
	"05": "0500-0600",
	"06": "0600-0700",
	"07": "0700-0800",
	"08": "0800-0900",
	"09": "0900-1000",
	"10": "1000-1100",
	"11": "1100-1200",
	"12": "1200-1300",
	"13": "1300-1400",
	"14": "1400-1500",
	"15": "1500-1600",
	"16": "1600-1700",
	"17": "1700-1800",
	"18": "1800-1900",
	"19": "1900-2000",
	"20": "2000-2100",
	"21": "2100-2200",
	"22": "2200-2300",
	"23": "2300-0000",
}

func main() {
	var logfile string

	logStats = make(map[string]*LogFormat, 0)
	flag.StringVar(&logfile, "f", "", "日志文件名称")
	flag.Parse()

	fmt.Println(logfile)
	if logfile == "" {
		log.Fatal(errors.New("未输入参数"))
	}
	// 读取日志
	f, err := os.Open(logfile)
	if err != nil {
		fmt.Println(err.Error())
	}
	log.Printf("开始分析日志文件%s", logfile)
	//建立缓冲区，把文件内容放到缓冲区中
	buf := bufio.NewReader(f)
	for {
		//遇到\n结束读取
		b, errR := buf.ReadBytes('\n')
		if errR != nil {
			if errR == io.EOF {
				break
			}
			fmt.Println(errR.Error())
		}

		//解析正则表达式，如果成功返回解释器
		reg := regexp.MustCompile(`(.+\s\d+:\d+:\d+?)\s(.+?)\s(.+?):\s(.+)`)
		if reg == nil {
			fmt.Println("regexp err")
			return
		}

		result := reg.FindAllStringSubmatch(string(b), -1)
		//range遍历数组
		for _, line := range result {
			if line[0] == " " {
				return
			}
			analyLog(line)
		}
	}
	if logStats != nil {
		logTimeStats := make([]LogFormat, 0)
		for _, v := range logStats {
			logTimeStats = append(logTimeStats, *v)
		}
		data, _ := json.Marshal(logTimeStats)
		dataPost(string(data))
	}
	log.Println("分析日志完成")
}

func analyLog(line []string) {
	dateTime := strings.Split(line[1], " ")[2]
	item := &LogFormat{}
	item.DeviceName = line[2]
	item.TimeWindow = time_windows[strings.Split(dateTime, ":")[1]]
	process := strings.Split(line[3], " ")
	for k, v := range process {
		if k == 0 { // 假设需要获取os.Args[k], k = 1
			item.ProcessId = v
		}
		if k == 1 { // 假设需要获取os.Args[k], k = 1
			item.ProcessName = v
		}
	}
	item.Description = line[4]

	idx := item.TimeWindow + "|" + item.DeviceName + "|" + item.ProcessId + "|" + item.ProcessName + "|" + item.Description
	idxmd5 := md5V(idx)
	if _, ok := logStats[idxmd5]; ok {
		logStats[idxmd5].NumberOfOccurrence = logStats[idxmd5].NumberOfOccurrence + 1
	} else {
		item.NumberOfOccurrence = 1
		logStats[idxmd5] = item
	}
}

// post数据
func dataPost(data string) {
	fmt.Println("post data=", string(data))

	resp := Post("https://foo.com/bar", data, "application/json")
	//resp := Post("http://127.0.0.1:5566/", data, "application/json")
	log.Printf("分析数据成功:%s", resp)
}

// post请求封装
func Post(url string, data interface{}, contentType string) (content string) {
	jsonStr, _ := json.Marshal(data)
	req, err := http.NewRequest("POST", url, bytes.NewBuffer(jsonStr))
	req.Header.Add("content-type", contentType)
	if err != nil {
		panic(err)
	}
	defer req.Body.Close()

	client := &http.Client{Timeout: 5 * time.Second}
	resp, error := client.Do(req)
	if error != nil {
		panic(error)
	}
	defer resp.Body.Close()

	result, _ := ioutil.ReadAll(resp.Body)
	content = string(result)
	return
}


func md5V(str string) string  {
	h := md5.New()
	h.Write([]byte(str))
	return hex.EncodeToString(h.Sum(nil))
}