import { Schema, model } from "mongoose";

export interface IUrl {
  shortenCode: string;
  initUrl: string;
}

const urlSchema = new Schema<IUrl>({
  shortenCode: String,
  initUrl: String,
});

export const UrlModel = model('Url', urlSchema);