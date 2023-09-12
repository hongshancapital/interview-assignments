import mongoose from "mongoose";
import { logger } from "../../src/util/log";

const testTeardown = async () => {
    logger.info('Stop on tests!!!!!!!');
    await mongoose.disconnect();
    logger.info('Mongoose closed');
};

export default testTeardown;

