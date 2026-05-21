import express from 'express';
import { createClient } from 'redis';
import genShort from '../tools/gen-short';

const router = express.Router();
const client = createClient({
    url: 'redis://localhost:6379'
});

let isRedisConnected = false;

client.on('error', () => {
    isRedisConnected = false;
    console.log('redis 连接错误');
});

client.on('reconnecting', () => {
    console.log('redis 正在重连...')
});

client.on('connect', () => {
    isRedisConnected = true;
    console.log('redis 连接成功');
});

client.connect();

router.post('/long2short', async function (req, res, next) {
    const long = req.body?.long as string;
    if (!long) {
        res.send({ success: false, msg: '缺少参数long' })
        return;
    }
    try {
        new URL(long)
    } catch(e) {
        res.send({ success: false, msg: '参数long不是正确的url格式' });
        return;
    }
    if (!isRedisConnected) {
        res.send({ success: false, msg: '系统错误' })
        return;
    }

    let short: string;
    do {
        short = genShort();
    } while (!await client.setNX(short, long));
    
    res.send({ success: true, data: short });
});

router.get('/*', async function (req, res, next) {
    const short = req.url.substr(1);
    if (!isRedisConnected) {
        res.send('系统错误');
        return;
    }
    const long = await client.get(short)
    if (long) {
        res.redirect(long);
    } else {
        next();
    }
});

export default router;
