import { Schema, model } from 'mongoose';

interface IUrlInfo {
    _id:String,
    // short: String,
    origin: String,
}

const urlInfoSchema = new Schema<IUrlInfo>({
    _id: { type: String, required: true },
    origin: { type: String, required: true },
},{
    versionKey: false
})

export const UrlInfo = model<IUrlInfo>('UrlInfo', urlInfoSchema);
 