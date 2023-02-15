import { Schema, model } from 'mongoose';
import { UrlData } from '../types/index';

const shortUrlSchema = new Schema<UrlData>({
  fullUrl: {
    type: String,
    required: true
  },
  shortUrl: {
    type: String,
    required: true
  },
  createTime: {
    type: Date,
    require: true,
    default: Date.now
  }
})

module.exports = model('ShortUrl', shortUrlSchema)