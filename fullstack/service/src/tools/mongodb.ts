import mongoose from 'mongoose';
import { getENV, system } from '@/configs';

const RECONNET_TIME = 5000;
const mongoconnect = () => {
    const mongodbAddress = getENV('DB_URL');

    if (!mongodbAddress) {
        return system('mongodb').error(`mongodb connect address is required but get ${mongodbAddress}`);
    }
    return mongoose.connect(mongodbAddress, {}, error => {
        if (error) {
            system('mongodb').error(error);
            setTimeout(mongoconnect, RECONNET_TIME);
        }
    });
};

export default new class MongoDB {
    constructor() {
        this.init();
    }

    private async init() {
        // 初始化操作
        this.server.once('connected', () => {// 连接成功
            system('mongodb').info(`mongodb connected on ${getENV('DB_URL')} success and ready to use.`);
        });

        this.server.on('disconnected', () => {// 连接失败或中断
            system('mongodb').fatal(`disconnected! connection is break off. it will be retried in ${RECONNET_TIME} ms after every reconnect until success unless process exit.`);
        });

        this.server.on('reconnected', () => {// 重新连接成功
            system('mongodb').info(`reconnect on ${getENV('DB_URL')} success and ready to use.`);
        });
        return await mongoconnect();
    }

    public get server() {
        return mongoose.connection;
    }

    public get schema() {
        return mongoose.Schema;
    }

    public get isOK() {
        return this.server.readyState === 1;
    }

    public async close(): Promise<void> {
        await this.server.close();
    }
};
