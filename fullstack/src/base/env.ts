import dotenv from 'dotenv';
// import { resolve } from 'path';

dotenv.config();
// dotenv.config({ path: resolve(__dirname, '..', '..', '..', '.env') });

export const { PORT, BASE_URL, MONGO_URI } = process.env;
