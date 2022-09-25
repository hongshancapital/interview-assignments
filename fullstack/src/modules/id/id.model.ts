import { Schema } from "mongoose";

export interface IDSchema {
  nodeId: number; // index
  count: number;
}

export const IDSchema = new Schema<IDSchema>({
  nodeId: { type: Number, required: true, index: true, unique: true },
  count: { type: Number, required: true },
});
