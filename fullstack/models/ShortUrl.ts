import { Schema, model } from 'mongoose';
import { IShortUrl } from '../types/url';

const ShortUrlSchema = new Schema<IShortUrl>({
    urlId: {
        type: String,
        required: true,
    },
    origUrl: {
        type: String,
        required: true,
    },
    shortUrl: {
        type: String,
        required: true,
    },
    clicks: {
        type: Number,
        required: true,
        default: 0,
    },
    date: {
        type: String,
        default: Date.now,
    },
});

export default model<IShortUrl>('Url', ShortUrlSchema);