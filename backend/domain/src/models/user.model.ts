import { model, Schema, Document } from "mongoose";
import { User } from "../interfaces/user.interface";

const userSchema: Schema = new Schema({
  uid: {
    type: String,
    required: true,
    unique: true,
    index: true,
  },
  username: {
    type: String,
    required: true,
    unique: true,
    index: true,
    trim: true,
  },
  password: {
    type: String,
    required: true,
  },
  salt: {
    type: String,
    required: true,
  },
  createTime: {
    type: Date,
    default: new Date(),
  },
  updateTime: {
    type: Date,
    default: new Date(),
  },
  available: {
    type: Boolean,
    default: 1,
  },
}, { shardKey: { uid: 'hashed' } });

export const UserModel = model<User & Document>("User", userSchema);
