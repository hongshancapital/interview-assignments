import * as mongoose from 'mongoose';
import {STATE} from "../common/constant";

export type shortUrlDocument = mongoose.Document & {
    oriUrl: string;
    shortUrl: string;
    state: string;
};

const shortUrlSchema = new mongoose.Schema<shortUrlDocument>(
    {
        oriUrl: {type: String, required: true},
        shortUrl: {type: String, required: true},
        state: {type: String, default: STATE.EFFECT, eum: STATE},
    },
    {timestamps: true},
);

//考虑到 使用短链接查询 长链接的情况多， 只建了一个索引
shortUrlSchema.index({shortUrl: 1});

export const ShortUrl = mongoose.model<shortUrlDocument>("shortUrl", shortUrlSchema);
