import mongoose from "mongoose";

const urlSchema = new mongoose.Schema({
    urlCode: String,
    longUrl: String
});

export const shortUrlModel = mongoose.model('ShortUrl', urlSchema)
