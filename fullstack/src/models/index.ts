import mongoose from 'mongoose';

const { Schema } = mongoose;

interface UrlData {
  shortUrlId: String;
  longUrl: String;
}

const UrlSchema: mongoose.Schema = new Schema<UrlData>({
  shortUrlId: { type: String, desc: '短连接id', index: true },
  longUrl: { type: String, desc: '原始链接', index: true },
});

export const UrlModel = mongoose.model('Url', UrlSchema);
