import Singleton from '../../utils/Singleton';
import Redis from 'ioredis';
import config from 'config';
import chalk from 'chalk';
class RedisCache extends Singleton {
    private cache: Redis;

    constructor() {
        super();

        this.cache = new Redis({host: config.get('redis.host'), port: config.get('redis.port')});

        this.cache.on('connect', () => {
            console.log(chalk.green(`Redis connection established`));
        });

        this.cache.on('error', error => {
            console.error(chalk.red(`Redis error, service degraded: ${error}`));
        });
    }

    async connect() {
        await this.cache.connect();
    }
    async get(key: string) {
        console.log(chalk.green(`Key ${key} request from Redis`));
        return await this.cache.get(key);
    }
    async set(key: string, value: string) {
        return await this.cache.set(key, value);
    }

    //only for jest test
    async flushall() {
        if (config.get('app.node_env') == 'test') {
            await this.cache.flushall();
        }
    }
    disconnect() {
        this.cache.disconnect();
    }
}
const RedisInstance = RedisCache.getInstance();
export default RedisInstance;
