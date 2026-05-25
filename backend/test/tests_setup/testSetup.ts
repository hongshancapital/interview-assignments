import "dotenv/config";
import { logger } from "../../src/util/log";
import env from "../../src/util/validateEnv";
import mongoose from "mongoose";

// https://mongoosejs.com/docs/jest.html#globalsetup-and-globalteardown
// Do not use globalSetup to call mongoose.connect() or mongoose.createConnection(). Jest runs globalSetup in a separate environment, so you cannot use any connections you create in globalSetup in your tests.
const testSetup = async () => {
    logger.info('Start on tests!!!!!!!');
    await mongoose.connect(env.MONGO_CONNECTION_STRING);
    mongoose.set('debug', true);

    logger.info("Mongoose connected");
};

export default testSetup;

