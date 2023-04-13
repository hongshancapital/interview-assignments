// 定义长链接和短链接的映射关系模型
import mongoose, {Model, Document, Schema} from "mongoose";

export interface UrlDocument extends Document {
    longUrl: string;
    shortUrl: string;
    createdAt: string;
    updatedAt: string;
    delete: boolean;
}

const urlSchema = new mongoose.Schema({
    longUrl: String,
    shortUrl: {
        type: String,
        index: true,
        required: true
    },
    createdAt: {type: Date, default: Date.now},
    updatedAt: {type: Date, default: Date.now},
    delete: {type: Boolean, default: false},
});

// @ts-ignore
const UrlModel: Model<UrlDocument> = mongoose.models.UrlModel || mongoose.model("UrlModel", urlSchema);

export default UrlModel;
