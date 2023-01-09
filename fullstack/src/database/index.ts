import mongoose from 'mongoose';
import { MONGODB_URL } from '../config';

export const connectMongoDb = async () => {
  try {
    await mongoose.connect(MONGODB_URL);
  } catch (error) {
    process.exit(1);
  }
};

mongoose.connection.on('connected', () => {
  console.log(`连接成功mongoose: ${MONGODB_URL}`);
});

mongoose.connection.on('error', (err) => {
  console.log(`mongoose 连接错误: ${err}`);
});

mongoose.connection.on('disconnected', () => {
  console.log('mongoose 连接断开');
});
