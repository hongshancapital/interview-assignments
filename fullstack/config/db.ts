import mongoose from 'mongoose';
import dotenv from 'dotenv';
dotenv.config({ path: './.env' });

const connectDB = async () => {

    const { MONGO_URI } = process.env
    if (!MONGO_URI) {
        process.exit(1);
    }
    
    try {
        await mongoose.connect(MONGO_URI, {
            useNewUrlParser: true,
            useUnifiedTopology: true
        });
    } catch (err) {
        console.error(err);
        
        process.exit(1);
    }
};

export default connectDB;