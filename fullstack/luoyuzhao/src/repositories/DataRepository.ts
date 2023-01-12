
import * as redis from 'redis';

class DataRepository {
    createClient() {
        let redisClient = redis.createClient({
            url: 'redis://127.0.0.1:6379',
            database: 0,
            password: '123456'
        });
        redisClient.connect();
        redisClient.on('error', (err) => {
            redisClient = null;
            console.log(err);
        })
        return redisClient;
    }

    async set(key: string, value: any): Promise<string> {
        return await this.createClient().set(key, value);
    }
    async delete(key: string): Promise<number> {
        return await this.createClient().del(key);
    }

    async get(key: string): Promise<string> {
        return await this.createClient().get(key);
    }

    async increase(key: string): Promise<number> {
        return await this.createClient().incr(key);
    }
    async exists(key: string): Promise<number> {
        return await this.createClient().exists(key);
    }
}
export default DataRepository;