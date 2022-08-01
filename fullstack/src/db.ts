/** @format */

import mongoose from 'mongoose';
import config from 'config';

const db = config.get('mongoURI') as string;

export const connectDB = async () => {
  try {
    await mongoose.connect(db, {
      useNewUrlParser: true,
    } as mongoose.ConnectOptions);
    console.log(`MongoDB Connected to: ${db}`);
  } catch (error: any) {
    console.error(error.message);
  }
};
