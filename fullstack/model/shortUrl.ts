import mongoose, { Schema } from 'mongoose';
interface UrlType extends mongoose.Document {
    longurl: String,
    urlid:String,
    delete: boolean
};
const UrlSchema: Schema<UrlType>  = new Schema({
    longurl:          String, 
    urlid:           String,                             //  Primary key
    delete:         { type: Boolean, default: false },            
  }, { 
    timestamps: true
});

const Url: mongoose.Model<UrlType> = mongoose.model('Url', UrlSchema);

export default Url;