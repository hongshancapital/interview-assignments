import mongoose from 'mongoose';

import ShortUrl from './ShortUrl';

mongoose.set('strictQuery', true);

const models = { ShortUrl };

export async function connectDb() {
  return mongoose.connect(process.env.MONGODB_URI, { useNewUrlParser: true, useUnifiedTopology: true });
}

export default models;
