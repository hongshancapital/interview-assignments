import { Document } from "mongoose";

/**
 * url interface
*/
export interface IUrl extends Document {
  rawUrl: string;
  shortenUrl: string;
  urlCode: string;
  createdAt: string;
}
