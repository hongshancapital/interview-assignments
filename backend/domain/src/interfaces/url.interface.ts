import { Document } from "mongoose";
export interface Url extends Document {
  uid: string;
  code: string;
  url: string;
  userUid: string;
  expiredTime?: Date;
  createTime: Date;
  updateTime: Date;
  available: boolean;
}
