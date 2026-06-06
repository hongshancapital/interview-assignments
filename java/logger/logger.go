package logger

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"time"

	rotatelogs "github.com/lestrrat-go/file-rotatelogs"
	"github.com/sirupsen/logrus"
)

//var logger *logrus.Entry

//rotatelogs 切分日志
//lfshook 分级别不同文件日志
func InitLogger(logPath string) {
	fmt.Println(logPath)
	exist, _ := checkPath(logPath)
	if !exist {
		// 创建文件夹
		err := os.MkdirAll(logPath, os.ModePerm)
		if err != nil {
			log.Fatal("init logger mkdir failed!", err)
		}
	}
	logrus.SetOutput(ioutil.Discard) //使用hook输出日志，丢弃原有的write操作

	logMain, err := rotatelogs.New(
		logPath+"/main.log.%Y%m%d%H",
		rotatelogs.WithMaxAge(30*24*time.Hour),
		rotatelogs.WithRotationTime(time.Hour),
	)
	if err != nil {
		log.Fatal("初始化日志失败")
	}
	logError, err := rotatelogs.New(
		logPath+"/error.log.%Y%m%d%H",
		rotatelogs.WithMaxAge(30*24*time.Hour),
		rotatelogs.WithRotationTime(time.Hour),
	)
	if err != nil {
		log.Fatal("初始化日志失败")
	}
	//为不同级别设置不同的输出目的
	lfHook := NewHook(
		WriterMap{
			logrus.DebugLevel: logMain,
			logrus.InfoLevel:  logMain,
			logrus.WarnLevel:  logMain,
			logrus.ErrorLevel: logError,
			logrus.FatalLevel: logError,
			logrus.PanicLevel: logError,
		},
		&logrus.JSONFormatter{},
	)
	logrus.AddHook(lfHook)

	//logger = logrus.WithField("host", "")
}

// 判断文件夹是否存在
func checkPath(path string) (bool, error) {
	_, err := os.Stat(path)
	if err == nil {
		return true, nil
	}
	if os.IsNotExist(err) {
		return false, nil
	}
	return false, err
}
