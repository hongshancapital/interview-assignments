import config from 'config';
import mongoose from 'mongoose';
import {logger} from "../logs/log4j.config";

const db = config.get('db.mongoURI') as string;

export const connectDB = async () => {
    try {
        await mongoose.connect(db, {
            useNewUrlParser: true
        });
        logger.info(`MongoDB Connected to: ${db}`);
        return true
    } catch (error) {
        logger.error(error)
        return false
    }
};
