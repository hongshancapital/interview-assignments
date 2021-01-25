package main

import (
	"errors"
	"fmt"
	"gopkg.in/gomail.v2"
	"io/ioutil"
	"net/http"
	"net/url"
	"os"
	"strings"
	"time"
)

type Mail struct {
	UserName string
	Password string
	From string
	To string
}
type Monitor struct {
	MonitorUrl string
}

func main()  {
	if len(os.Args) < 6{
		fmt.Print("args is [url mail_username mail_pwd mail_from mail_to]")
		os.Exit(0)
	}
	m := new(Monitor)
	m.MonitorUrl = os.Args[1]
	mail := new(Mail)
	mail.UserName = os.Args[2]
	mail.Password = os.Args[3]
	mail.From = os.Args[4]
	mail.To = os.Args[5]

	errorCount := 0
	errorMsg := ""
	for range time.Tick(time.Minute*2){
		err :=m.monitor()
		if err !=nil{
			errorCount++
			errorMsg = errorMsg+" "+err.Error()
		}
		if errorCount>= 3{
			mail.sendMail(errorMsg)
			errorCount = 0
			errorMsg = ""
		}
	}
}


func (m Monitor)monitor()error{
	client := &http.Client{
		CheckRedirect: func(req *http.Request, via []*http.Request) error {
			return http.ErrUseLastResponse
		},
	}
	testUrl := "https://www.baidu.com"
	data := url.Values{"url": {testUrl},"expire":{}}
	resp,err:=http.PostForm(m.MonitorUrl+"/gen",data);
	if err != nil {
		return err
	}
	shortUrl,err:=ioutil.ReadAll(resp.Body);
	if err != nil {
		return err
	}
	resp,err=client.Get(string(shortUrl));
	if err != nil{
		return err
	}

	get,_:=ioutil.ReadAll(resp.Body);

	if resp.StatusCode != 301{
		fmt.Println(resp.Status,get)
		return errors.New("resp.Status not 301")
	}
	location := resp.Header.Get("Location")
	if !strings.EqualFold(location,testUrl){
		return errors.New(location+" not eq "+testUrl)
	}
	return nil
}

func (mail Mail)sendMail(errorContent string)  {
	m := gomail.NewMessage()
	m.SetHeader("From", mail.From)
	m.SetHeader("To", mail.To)           //收件人
	m.SetHeader("Subject", "short url error!")                     //邮件标题
	m.SetBody("text/html", errorContent)     //邮件内容

	d := gomail.NewDialer("smtp.exmail.qq.com",465, mail.UserName,mail.Password)
	if err := d.DialAndSend(m); err != nil {
		fmt.Println(err)
	}
}