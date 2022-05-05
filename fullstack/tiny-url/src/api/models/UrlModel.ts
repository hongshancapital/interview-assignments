import {Document, Schema, model} from 'mongoose';
export interface UrlAttributes extends Document {
    url: string;
    shortId: string;
    createdAt: Date;
    updatedAt: Date;
}

export const UrlSchema = new Schema<UrlAttributes>(
    {
        url: {
            type: String,
            required: true,
            unique: true,
        },
        shortId: {
            type: String,
            required: true,
            unique: true,
            index: true,
        },
    },
    {timestamps: {createdAt: true, updatedAt: true}},
);

const Urlmodel = model<UrlAttributes>('tinyurl', UrlSchema, 'tinyurl');
export default Urlmodel;
