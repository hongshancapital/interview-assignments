import logger from '../util/logger'
import dotenv from "dotenv";
import fs from "fs";

if (fs.existsSync(".env")) {
    logger.debug("Using .env file to supply config environment variables");
    dotenv.config({ path: ".env" });
} else {
    logger.debug("Using .env.example file to supply config environment variables");
    dotenv.config({ path: ".env.example" });  // you can delete this after you create your own .env file!
}
export const ENVIRONMENT = process.env.NODE_ENV;
const prod = ENVIRONMENT === "production"; // Anything else is treated as 'dev'

export const POSTGRES_URI = prod ? process.env["POSTGRES_URI"] : process.env["POSTGRES_URI_LOCAL"];
export const REDIS_OPTION = prod ? process.env["REDIS_OPTIONS"] : process.env["REDIS_OPTIONS_LOCAL"];


if (!POSTGRES_URI) {
    if (prod) {
        logger.error("No mongo connection string. Set POSTGRES_URI environment variable.");
    } else {
        logger.error("No mongo connection string. Set POSTGRES_URI_LOCAL environment variable.");
    }
    process.exit(1);
}
