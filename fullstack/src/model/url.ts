/** @format */

import mongoose from 'mongoose';

const urlSchema = new mongoose.Schema({
  urlCode: String,
  longUrl: String,
});

export default mongoose.model('Url', urlSchema);
