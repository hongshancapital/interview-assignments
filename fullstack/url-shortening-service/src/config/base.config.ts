import * as dotenv from 'dotenv';
dotenv.config();

const config = {
    BASE_URL: process.env.BASE_URL ? process.env.BASE_URL : 'http://localhost',
    PORT: process.env.PORT ? process.env.PORT : 5000,
}

export default config;