package util

import (
	"errors"
	"net/http"
	"short_domain/def"

	"github.com/gin-gonic/gin"
)

const (
	BaseStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
	BaseNum = 62
)

func IntTo62(id int64) (string, error) {

	ss := make([]byte, 8)
	i := 7
	for id != 0 {
		index := id % BaseNum
		id = id / BaseNum
		ss[i] = BaseStr[index]
		i--
		if i < 0 {
			return "", errors.New("超出系统限制")
		}
	}
	for ; i >= 0; i-- {
		ss[i] = '0'
	}
	return string(ss), nil
}

func BuildRsp(ctx *gin.Context, err error, data gin.H) {
	if err != nil {
		ctx.JSON(http.StatusOK, gin.H{
			"code": def.CodeErr,
			"msg":  err.Error(),
			"data": gin.H{},
		})
		return
	}
	ctx.JSON(http.StatusOK, gin.H{
		"code": def.CodeOk,
		"msg":  "ok",
		"data": data,
	})
}
