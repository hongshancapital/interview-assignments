import { IUrl } from "../interfaces/IUrl";
import MongoDbConnection from "../config/db";
import { Schema } from "mongoose";
/**
 * Url model definition
*/

const {
  mongo: { model },
} = MongoDbConnection;

const UrlSchema: Schema<IUrl> = new Schema<IUrl>({
  rawUrl: { type: String, required: true },
  shortenUrl: { type: String },
  urlCode: { type: String },
  createdAt: { type: String },
});

export default model<IUrl>("UrlShorten", UrlSchema);
