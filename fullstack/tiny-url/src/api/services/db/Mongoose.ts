import Singleton from '../../utils/Singleton';
import mongoose from 'mongoose';
import UrlModel from '../../models/UrlModel';
import config from 'config';
import chalk from 'chalk';

class Mongdb extends Singleton {
    async connect() {
        try {
            await mongoose.connect(config.get('mongodb.mongodburl'));
            console.log(chalk.green(`MongoDB connection established`));
        } catch (error) {
            console.error(chalk.red(error));
        }
    }
    async disconnect() {
        await mongoose.connection.close();
    }

    async getUrl(shortId: object) {
        return await UrlModel.findOne(shortId);
    }

    async createUrl(newUrl: object) {
        return await UrlModel.create(newUrl);
    }

    //only for jest test
    async deleteMany() {
        if (config.get('app.node_env') == 'test') {
            return await UrlModel.deleteMany();
        }
    }
}
const MongdbInstance = Mongdb.getInstance();
export default MongdbInstance;
