// 定义长链接和短链接的映射关系模型
import mongoose, {Model, Document} from "mongoose";

export interface UrlDocument extends Document {
    longUrl: string;
    shortUrl: string;
}
 const urlSchema = new mongoose.Schema({
    longUrl: String,
    shortUrl: String
});
const UrlModel:Model<UrlDocument> = mongoose.models.UrlModel || mongoose.model<UrlDocument>("UrlModel", urlSchema);

export default UrlModel;
