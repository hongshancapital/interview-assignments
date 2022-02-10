FROM alpine:latest
# 构建基础镜像 OPEN-JDK11, 镜像上传至镜像仓库
LABEL author=huangshengtao

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && apk add -U --no-cache tzdata ttf-dejavu fontconfig openjdk11 && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && apk add --no-cache font-adobe-100dpi && mkdir -p /usr/share/fonts/win && mkdir /app && wget -P /usr/share/fonts/win/ http://fnd.foo.com/devops/winfonts/simsun.ttc && fc-cache -f
