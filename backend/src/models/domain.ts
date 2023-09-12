import mongoose from 'mongoose';

const schema = new mongoose.Schema({
    name: {
        type: String,
        trim: true
    },
    url: {
        type: String,
        required: true,
        trim: true
    },
    compressed: {
        type: String,
        required: true,
        trim: true,
        unique: true // 添加唯一索引
    }
}, { timestamps: { createdAt: true, updatedAt: true }, versionKey: false });

export default mongoose.model('domain', schema, 'domain');
