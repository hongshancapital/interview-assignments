import mongoose, { Model } from "mongoose";
const Schema = mongoose.Schema;
interface IUrlModel {
  _id: string,
  longUrl: string
}
let urlModel!: Model<IUrlModel>;
mongoose.connect('mongodb://127.0.0.1/dev');
const UrlSchema = new Schema({
  _id: String,
  longUrl: String,
});
urlModel = mongoose.model<IUrlModel>('url', UrlSchema);
export const model = urlModel;