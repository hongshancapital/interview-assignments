import { Document, Schema, model } from 'mongoose'

export interface IUrl extends Document {
    url: string;
    shortId: string;
    shortened: string;
    createdAt: Date;
    updatedAt: Date;
}

const UrlSchema = new Schema({
    url: {
        type: String,
        required: true,
        unique: true,
        index: true
    },
    shortId: {
        type: String,
        required: true,
        unique: true,
        index: true
    },
    shortened: {
        type: String,
        required: true,
        unique: true,
    }
}, { timestamps: { createdAt: true, updatedAt: true } });

export default model<IUrl>('Url', UrlSchema);