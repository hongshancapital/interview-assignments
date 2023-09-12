import * as dotenv from 'dotenv';
dotenv.config();

const config = {
    MONGO_URL: process.env.MONGO_URL ? process.env.MONGO_URL : 'mongodb://localhost:27017/url-shortening-service',
    DB_NAME: process.env.DB_NAME ? process.env.DB_NAME : 'urls',
    PRIMARY_KEY: process.env.PRIMARY_KEY ? process.env.PRIMARY_KEY : 'urlCode'
}

export default config;