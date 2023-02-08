import ioredis from 'ioredis';
import { getENV, system } from '@/configs';

const RECONNET_TIME = 5000;

export default new class Redis {
    private service: ioredis | undefined;
    constructor() {
        if (!this.isUseful) {
            return;
        }
        this.init();
    }

    private init(): void {
        this.service = new ioredis(getENV('REDIS_URL') as string, {
            enableReadyCheck: true,
            retryStrategy(times) {
                if (times <= 5) {
                    system('redis').fatal(`connection is break off! disconnect time is ${times} but still trying again after ${RECONNET_TIME}ms.`);
                    return RECONNET_TIME;
                }
                system('redis').fatal('connection is disconnected!');
                return;
            }
        });

        this.service.on('connect', () => {
            system('redis').info(`redis connected on ${getENV('REDIS_URL')} success and ready to use.`);
        });

        this.service.on('error', error => {
            system('redis').error(error);
        });
    }

    private get isUseful() {
        return Boolean(getENV('REDIS_URL'));
    }

    public get server() {
        if (!this.isUseful) {
            system('redis').warn('there is no redis config, but call it! redis is not available!');
        }
        return this.service;
    }

    public get isOK() {
        return !this.isUseful || this.isUseful && this.service?.status === 'ready';
    }

    public async close(): Promise<'OK' | undefined> {
        return await this.service?.quit();
    }
};
