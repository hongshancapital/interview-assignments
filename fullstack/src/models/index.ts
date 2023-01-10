
import mongoose from "mongoose";
import { db } from '../config/default';

export const connectDB = async () => {
  try {
    mongoose.connect(db.mongoUri);
    console.log(`MongoDB Connected to: ${db}`);
  } catch (error) {
    console.error(error.message);
    process.exit(1);
  }
};