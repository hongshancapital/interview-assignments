import express from 'express';
import ShortUrlController from './shortUrlController';

// 比较常见的做法是通过Decorator来做定义，在这里解析并绑定Handler。当前实现从简，直接通过属性定义
export default {
  Bind(server: express.Express) {
    const shortUrlController =  new ShortUrlController();
    server.get(shortUrlController.path, shortUrlController.get);
    server.post(shortUrlController.path, shortUrlController.post);
  }
}