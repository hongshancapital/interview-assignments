import mongoose from "mongoose";
const db = 'mongodb://localhost:27017/url-short';

export const connectDB = async () => {
  try {
    await mongoose.connect(db, {
      useNewUrlParser: true
    });
    console.log(`MongoDB Connected to: ${db}`);
  } catch (error) {
    console.log(error.message)
    process.exit(1);
  }
}