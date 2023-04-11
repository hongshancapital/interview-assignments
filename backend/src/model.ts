import { Schema, model } from 'mongoose';

interface IUrlInfo {
    _id:String,
    // short: String,
    origin: String,
}

const urlInfoSchema = new Schema<IUrlInfo>({
    _id: { type: String, required: true },
    // short: { type: String, required: true },
    origin: { type: String, required: true },
})

export const UrlInfo = model<IUrlInfo>('UrlInfo', urlInfoSchema);
 