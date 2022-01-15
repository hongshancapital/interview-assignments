package main

import (
	"flag"
	"net/http"
	"short_domain/config"
	"short_domain/controller"
	"short_domain/logger"
	"time"

	"github.com/facebookgo/grace/gracehttp"
	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
)

func main() {
	port := flag.String("port", "8082", "port")
	//log.NewPrivateLog("api")
	config.InitConfig("config/conf.toml")
	logger.InitLogger(config.GetLogPath())
	startHttp(*port)
}

func startHttp(port string) {
	r := newGin()
	server := &http.Server{
		Addr:         ":" + port,
		ReadTimeout:  20 * time.Second,
		WriteTimeout: 20 * time.Second,
		Handler:      r,
	}
	err := gracehttp.Serve(server)
	if err != nil {
		logrus.Fatal("服务启动失败:", err.Error())
	}
}

func newGin() *gin.Engine {
	r := gin.New()
	r.Use(gin.Recovery())
	domain := r.Group("/domain")
	{
		domain.POST("/add_url", controller.AddLongUrl)
		domain.GET("/get_url", controller.GetLongUrl)
	}
	return r
}
