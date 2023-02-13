/*
 * @Author: Json Chen
 * @Date: 2023-02-13 17:05:57
 * @LastEditors: Json Chen
 * @LastEditTime: 2023-02-13 18:07:24
 * @FilePath: /interview-assignments/fullstack/src/connections/mongo.ts
 */


import mongoose from 'mongoose';

// Export the connection
export const connect = () => {
  try {
    mongoose.Promise = Promise;
    const mongourl ='mongodb://localhost:27017/test'
    console.log('mongourl: ', mongourl);
    mongoose.set('strictQuery', false);
    mongoose.connect(mongourl);
    console.log("Database online...");
  } catch (err) {
    console.error("Error:", err);
    throw new Error("Error connecting to database!");
  }
};

// export the disconnection method, this is going to be used for testing purposes
export const disconnect = (done:any) => {
  mongoose.disconnect(done);
  console.log("\n\t\t-- Database offline --");
};
