/** @format */

import config from 'config';
import mongoose from 'mongoose';

export class Db {
  private static mongoURI: string = config.get('mongoURI');

  /**
   * 链接mongodb数据库
   */
  public static async connectDB() {
    try {
      await mongoose.connect(Db.mongoURI);
      console.log(`MongoDB Connected to: ${Db.mongoURI}`);
    } catch (error) {
      if (error instanceof Error) {
        console.error(error.message);
      } else {
        console.error('Unexpected error', error);
      }
    }
  }
}
