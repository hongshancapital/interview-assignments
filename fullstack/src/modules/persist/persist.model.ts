import { Schema } from "mongoose";

export interface TinyURLSchema {
  alias: string; // index
  url: string;
  expireAt: number;
}

export const TinyURLSchema = new Schema<TinyURLSchema>({
  alias: { type: String, required: true, index: true, unique: true },
  url: { type: String, required: true },
  expireAt: { type: Number, required: true },
});
