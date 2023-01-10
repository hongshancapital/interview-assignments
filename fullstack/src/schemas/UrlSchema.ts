import { Schema, model, Document } from 'mongoose';

type Url = Document & {
  originalUrl: string;
  shortPath: string;
};

const UrlSchema = new Schema({
  // 创建索引
  originalUrl: {
    type: String,
    required: true,
    trim: true,
    index: true,
  },
  // 创建索引
  shortPath: {
    type: String,
    required: true,
    trim: true,
    index: true,
  },
  createdAt: {
    type: Date,
    required: true,
    default: Date.now(),
  },
});

export default model<Url>('Url', UrlSchema);
