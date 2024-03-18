import { InferSchemaType, Schema, model } from "mongoose";

export const SystemNameId = "id";

const idSchema = new Schema({
    // 转化后的url，只存储八位字符
    id: {type: Schema.Types.BigInt, unique: true, required: true},
    name: {type: Schema.Types.String, required: true, unique: true, enum: [SystemNameId]},
}, { timestamps: true})

type Url = InferSchemaType<typeof idSchema>;
export default model<Url>("SystemId", idSchema, "System");