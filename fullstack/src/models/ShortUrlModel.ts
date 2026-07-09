import { Document, Model, Schema, model } from 'mongoose'

export interface IUrl extends Document {
    url: string; //index
    shortId: string; //index
    shortUrl: string;
    createdAt: Date;
    updatedAt: Date;
}

const UrlSchema = new Schema({
    url: {
        type: String,
        required: true,
        unique: true,
    },
    shortId: {
        type: String,
        required: true,
        unique: true,
    },
    shortUrl: {
        type: String,
        required: true,
        unique: true,
    }
}, { timestamps: { createdAt: true, updatedAt: true } });

export default model<IUrl>('Url', UrlSchema);