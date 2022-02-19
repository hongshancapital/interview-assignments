package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"log"
	"os"
	"regexp"
	"time"
)

const (
	TimeFormat        = "Jan 2 15:04:05"
	TimeRegexpFormat  = `([A-Za-z]{3}\s*[0-9]{1,2}\s[0-9]{2}:[0-9]{2}:[0-9]{2})`
	DeviceFormat      = `([0-9A-Za-z]+)`
	ProcessFormat     = `(.*)`
	IdRegexpFormat    = `(\[[0-9]{2,5}\])`
	DescriptionFormat = `(.*)`
	RegexpFormat      = TimeRegexpFormat + ` ` + DeviceFormat + ` ` + ProcessFormat + IdRegexpFormat + `: ` + DescriptionFormat
)

type FormatLog struct {
	TimeWindow  time.Time `json:"time_window"`
	DeviceName  string    `json:"device_name"`
	ProcessName string    `json:"process_name"`
	ProcessId   string    `json:"process_id"`
	Description string    `json:"client_hostname"`
}

func main() {
	lines, err := readLines("./DevOps_interview_data_set")
	if err != nil {
		log.Fatalf("readLines failed, err: %s", err)
	}

	var doc []FormatLog

	for _, line := range lines {
		//fmt.Println(line)
		formatLog := Parse(line)

		doc = append(doc, formatLog)

	}
	//fmt.Println(doc)
	data, err := json.Marshal(doc)
	if err != nil {
		log.Fatal("json marshal failed, err:", err)
		return
	}
	fmt.Printf("%s\n", data)
}

func readLines(path string) ([]string, error) {
	file, err := os.Open(path)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var lines []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}
	return lines, scanner.Err()
}

func Parse(text string) FormatLog {
	re, err := regexp.Compile(RegexpFormat)
	if err != nil {
		log.Fatalf("regexp: %s", err)
	}
	var groups = make([]string, 8)
	groups = re.FindStringSubmatch(text)
	if groups != nil {
		t, err := time.Parse(TimeFormat, groups[1])
		if err != nil {
			log.Println("parse time failed.")
		}

		formatLog := FormatLog{
			TimeWindow:  t,
			DeviceName:  groups[2],
			ProcessName: groups[3],
			ProcessId:   groups[4],
			Description: groups[5],
		}
		return formatLog
	}
	return FormatLog{}
}
