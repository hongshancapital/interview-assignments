import { Injectable, Scope } from '@nestjs/common';
import * as redis from 'redis';
import { redisConfig } from '../constants'

@Injectable()
export class RedisService {
    public client: any;
    public connected: boolean;
    constructor() {
        this.connected = false;

        this.client = redis.createClient({
            ...redisConfig
        });

        this.client.on('connect', () => {
            this.connected = true;
        });

        this.client.on('end', () => {
            this.connected = false;
        });

        this.client.on('error', err => {
            this.connected = false;
        });
    }

    async get(key): Promise<any> {
        return new Promise((resolve, reject) => {
            this.client.get(key, (err, reply) => {
                if (err) {
                    reject(new Error('redis error: ' + err));
                } else if (reply) {
                    resolve(reply.toString());
                } else {
                    resolve(null);
                }
            });
        });
    }

    async set(key, value, expireS) {
        return new Promise((resolve, reject) => {
            this.client.set(key, value, 'EX', expireS, (err, reply) => {
                if (err) {
                    reject(err);
                } else {
                    resolve(null);
                }
            });
        });
    }
}
