import * as redis from "redis";
import {ShorterListGenerator} from "./generate_shorter";
import {BaseUrl, ExpireTime} from "../server";

console.log(process.env.REDIS_URL)
export const redisUrl = process.env.REDIS_URL || 'redis://localhost:6379';
export const client = redis.createClient({url: redisUrl});

// 上游构件，此处不需模拟测试
client.on('error', (err) => {
    console.log('Redis Client Error', err);
});

(async () => {
    await client.connect();

    await client.configSet("notify-keyspace-events", "Ex");
    const sub = client.duplicate();
    await sub.connect();
    await sub.subscribe("__keyevent@0__:expired", MrProper);
}) ();

export async function SyncShortToRedis() {
    const synSym = await client.exists("shortname-generator");
    if (synSym === 0) {
        console.log(`[server]: syncing redis data, may take a few minutes, please wait`)

        const ids = ShorterListGenerator();
        for (let i in ids) {
            await client.sendCommand(["RPUSH", "shortname-generator", ids[i]]);

            if (parseInt(i) % 10000 === 0) {
                console.log(`[server]: sync redis data progress: ${i}/${ids.length}`);
            }
        }

        console.log(`[server]: sync redis data progress: ${ids.length}/${ids.length}`);
    }
}

export async function MrProper(key: string) {
    const recycle = key.replace(BaseUrl, "");
    await client.rPush('shortname-generator', recycle);
}

export async function GetLongNameFromRedis(longName: string) {
    let shorter = await client.hGet('generated-longname', longName)
    const shorterExists = shorter? await client.EXISTS(shorter) : 0;

    if (!shorter || shorterExists === 0) {
        const geneShorter = await client.lPop("shortname-generator");
        shorter = BaseUrl + geneShorter;

        await client.set(shorter, longName, {EX: ExpireTime});
        await client.hSet("generated-longname", longName, shorter);
    }

    return shorter;
}