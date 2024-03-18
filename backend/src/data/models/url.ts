import { InferSchemaType, Schema, model } from "mongoose";

export const UrlStatusOpen = "o";
export const UrlStatusClosed = "c";

export const originUrlMaxLength = 512;
export const descriptionMaxLength = 256;
export const creatorMaxLength = 32;


const urlSchema = new Schema({
    // 转化后的url，只存储八位字符
    shortenUrl: {type: Schema.Types.String, required: true, unique: true, maxLength: 8},
    // 原始url，最长512位字符
    originUrl: {type: Schema.Types.String, required: true, maxLength: originUrlMaxLength},
    // 描述，最长256位字符
    description: {type: Schema.Types.String, required: true, maxLength: descriptionMaxLength},
    // 创建者id
    creator: {type: Schema.Types.String, required: true, maxLength: creatorMaxLength},
    // 过期时间
    expires: {type: Schema.Types.Date, default: null},
    // 状态: "o": open，可用 "c": close，不可用
    status: {type: Schema.Types.String, enum: [UrlStatusOpen, UrlStatusClosed], default: UrlStatusOpen}
}, { timestamps: true});

type Url = InferSchemaType<typeof urlSchema>;
export default model<Url>("Url", urlSchema);