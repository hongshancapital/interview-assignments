import { Document } from "mongoose";

/**
 * url interface
*/
export interface IUrl extends Document {
  _id: string;
  rawUrl: string;
  shortenUrl: string;
  urlCode: string;
  createdAt: string;
}
