import mongoose from "mongoose";

const UrlSchema = new mongoose.Schema({
  urlCode: String, // index
  longUrl: String // index
});

export const UrlModel: mongoose.Model<any> = mongoose.model('url', UrlSchema);