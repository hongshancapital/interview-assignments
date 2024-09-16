import mongoose from 'mongoose';

export interface ShortUrlDocument extends mongoose.Document {
  longUrl: string;
  shortUrl: string;
}

const ShortUrlSchema = new mongoose.Schema(
  {
    longUrl: { type: String, required: true },
    shortUrl: { type: String, required: true, unique: true },
  },
  { timestamps: true }
);

export const ShortUrlModel = mongoose.model<ShortUrlDocument>('ShortUrl', ShortUrlSchema);