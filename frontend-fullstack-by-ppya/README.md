# 短域名和轮播一体化项目

## 短域名
访问: http://localhost:3000/#/short

### 短域名服务  api/lambda
* 1. 生成短链：/link/short
* 2. 跟进短域获取长域名：/link/long
* 3. 访问短域名，重定向至长域名：/ppya.cn/t/:schema

## 轮播 components/Carousel

- 启动
```js
npm run start
```

- 访问查看效果
```js
http://localhost:3000
```

- 轮播支持参数
```js
interface CarouselPropType {
  autoPlay?: boolean; // 是否自动轮播
  activeIndex?: number; // 初始化轮播index
  dataSource: CarouselItemPropType[]; // 数据源
  duration?: number; // 停留时间
  speed?: number; // 播放速度
  indicators?: boolean;
}

```