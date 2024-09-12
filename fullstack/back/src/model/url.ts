import mongoose from 'mongoose';
const Schema: typeof mongoose.Schema = mongoose.Schema;

interface UrlData { 
    short_url: String;
    long_url: String;
    hash_url: String;
    create_time: Date;
    update_time: Date;
}
 
const UrlSchema: mongoose.Schema = new Schema<UrlData>({ 
    short_url: { type: String, desc: '短码', index: true },
    long_url: { type: String, desc: '原始链接', index: true },
    hash_url: { type: String, desc: '索引 MD5', index: true },
    create_time: { type: Date, default: Date.now },
    update_time: { type: Date, default: Date.now }
});

const UrlModel = mongoose.model('Url', UrlSchema);
export { UrlModel, UrlData };
