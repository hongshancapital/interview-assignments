import { Schema, model, Document } from "mongoose";

export interface IShortUrl extends Document {
  longUrl: string;
  shortCode: string;
}

const ShortUrlSchema = new Schema<IShortUrl>({
  longUrl: { type: String, required: true },
  shortCode: { type: String, required: true, unique: true },
});

const ShortUrl = model<IShortUrl>("ShortUrl", ShortUrlSchema);

export default ShortUrl;
