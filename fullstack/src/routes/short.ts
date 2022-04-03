import express from 'express';
import { createClient } from 'redis';

const router = express.Router();
const client = createClient();

let isRedisConnected = false;

(async () => {
    await client.connect().then(res => {
        console.log('redis 连接成功');
        isRedisConnected = true;
    }, (err) => {
        console.log('redis 连接失败');
        console.error(err)
    });
    client.on('error', () => {
        isRedisConnected = false;
    })
})()

router.get('/long2short', async function (req, res, next) {
    const long = req.query.long as string;
    if (!long) {
        res.send({ success: false, msg: '缺少参数long' })
        return;
    }
    if (!isRedisConnected) {
        res.send({ success: false, msg: '系统错误' })
        return;
    }
    let short: string;
    do {
        short = Math.random().toString(36).substr(-8);
    } while (await client.get(short));

    await client.set(short, long);
    res.send({ success: true, data: short });
});

router.get('/short2long', async function (req, res, next) {
    const short = req.query.short as string;
    if (!short) {
        res.send({ success: false, msg: '缺少参数short' });
        return;
    }
    if (!isRedisConnected) {
        res.send({ success: false, msg: '系统错误' })
        return;
    }
    const long = await client.get(short)
    if (long) {
        res.send({ success: true, data: long })
    } else {
        res.send({ success: false, msg: '没有找到对应的长连接' })
    }
});

export default router;
