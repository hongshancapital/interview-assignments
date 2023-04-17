import { Schema, Document, model } from 'mongoose'

export interface Url {
  shorturl: string
  url: string
}

export interface UrlModel extends Url, Document {
  createTime: Date
}

const itSchema = new Schema({
  shorturl: {
    type: String,
    unique: true, // 唯一索引
    index: true,
  },
  url: {
    type: String,
    unique: true, // 唯一索引
    index: true,
  },
  _createTime: { type: Date, default: Date.now }
}, {
  versionKey: false
})

itSchema.virtual('createTime')
  .set(function (value) { this._createTime = value })
  .get(function () { return this._createTime.toLocaleString() })

export default model<UrlModel>('Url', itSchema)
