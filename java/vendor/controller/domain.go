package controller

import (
	"net/http"
	"short_domain/service"
	"short_domain/util"

	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
)

func AddLongUrl(ctx *gin.Context) {

	var params struct {
		Url string `form:"url" json:"url" binding:"required"`
	}
	if err := ctx.ShouldBind(&params); err != nil {
		ctx.JSON(http.StatusOK, gin.H{"code": 0})
	}

	logrus.WithFields(logrus.Fields{
		"ip":  ctx.ClientIP(),
		"url": params.Url,
	}).Info("add_url")

	url, err := service.NewShortUrlService().AddLongUrl(params.Url)
	logrus.Info("here")
	util.BuildRsp(ctx, err, gin.H{"url": url})
}

func GetLongUrl(ctx *gin.Context) {
	var params struct {
		Url string `form:"url" json:"url" binding:"required"`
	}
	if err := ctx.ShouldBind(&params); err != nil {
		ctx.JSON(http.StatusOK, gin.H{"code": 0})
	}
	url := service.NewShortUrlService().GetByShortUrl(params.Url)
	util.BuildRsp(ctx, nil, gin.H{"url": url})
}
