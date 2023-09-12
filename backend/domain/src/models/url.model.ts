import { model, Schema, Document } from "mongoose";
import { Url } from "../interfaces/url.interface";

const urlSchema: Schema = new Schema({
  uid: {
    type: String,
    required: true,
    unique: true,
    index: true
  },
  code: {
    type: String,
    required: true,
    unique: true,
    index: true,
    maxLength: 8,
  },
  url: {
    type: String,
    required: true,
    trim: true,
    index: 'hashed'
  },
  userUid: {
    type: String,
    required: true,
    index: true,
  },
  expiredTime: {
    type: Date,
  },
  createTime: {
    type: Date,
    default: new Date(),
  },
  updateTime: {
    type: Date,
    default: new Date(),
  },
  available: {
    type: Boolean,
    default: 1,
  },
}, { shardKey: { uid: 'hashed' } });

export const UrlModel = model<Url & Document>("Url", urlSchema);
