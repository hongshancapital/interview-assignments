import { Schema, model, Document } from 'mongoose';

export interface IShortUrl extends Document {
  shortUrl: string;
  longUrl: string;
}

const schema = new Schema<IShortUrl>(
  {
    shortUrl: {
      type: String,
      required: true,
    },
    longUrl: {
      type: String,
      required: true,
    },
  },
  { timestamps: true },
);

export default model<IShortUrl>('ShortUrl', schema);
