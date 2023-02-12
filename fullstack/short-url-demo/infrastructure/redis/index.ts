import { createClient } from 'redis';

const client = createClient();
client.on('error', err => console.log('Redis Client Error', err));

async function connect(): Promise<void> {
    await client.connect();
    console.log("redis connnecting....")
}

async function disConnect(): Promise<void> {
    await client.disconnect();
    console.log("redis disConnnected")
}

async function set(key: string, value: string) {
    await client.set(key, value);
}

async function get(key: string) {
    return await client.get(key);
}

async function incrBy(key: string, increment: number) {
    return client.incrBy(key, increment);
}

export default {
    connect,
    disConnect,
    set,
    get,
    incrBy
};

