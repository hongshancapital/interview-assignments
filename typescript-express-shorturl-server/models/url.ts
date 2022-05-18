
/**
 * url.ts
 * @authors lizilong
 * @description 数据模型配置文件
 */

import mongoose from 'mongoose'

// 为 urlCode 和 longUrl 分别创建 index 索引，便于快速双向查找

// 创建索引SQL语句如下：
// db.getCollection('urls').createIndex( { "urlCode": 1 } )
// db.getCollection('urls').createIndex( { "longUrl": 1 } )

// NoSQL 的 Schema 如下
const urlSchema = new mongoose.Schema({
	urlCode: String,
	longUrl: String,
})

export default mongoose.model('Url', urlSchema)
